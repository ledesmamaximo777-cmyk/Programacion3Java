package com.inventory.smart.controller;

import com.inventory.smart.dto.ProductoRequest;
import com.inventory.smart.dto.ProductoResponse;
import com.inventory.smart.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller REST para la gestión de productos del inventario.
 *
 * <p>Expone los endpoints CRUD más búsqueda y ordenamiento de productos.
 * Delega toda la lógica de negocio a {@link ProductoService} y se limita
 * a la recepción/conversión de requests HTTP y la construcción de respuestas.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "CRUD y consultas de productos del inventario")
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Constructor con inyección de dependencias (DIP).
     *
     * @param productoService servicio de gestión de productos
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Lista todos los productos con filtros opcionales.
     *
     * @param categoriaId filtra por ID de categoría (opcional)
     * @param precioMin   precio mínimo inclusive (opcional)
     * @param precioMax   precio máximo inclusive (opcional)
     * @param enStock     si {@code true}, solo productos con stock &gt; 0 (opcional)
     * @return 200 OK con la lista de productos que cumplen los filtros
     */
    @GetMapping
    @Operation(summary = "Listar productos con filtros opcionales")
    public ResponseEntity<List<ProductoResponse>> listarTodos(
            @Parameter(description = "Filtrar por ID de categoría")
            @RequestParam(required = false) Long categoriaId,
            @Parameter(description = "Precio mínimo")
            @RequestParam(required = false) Double precioMin,
            @Parameter(description = "Precio máximo")
            @RequestParam(required = false) Double precioMax,
            @Parameter(description = "Solo productos en stock")
            @RequestParam(required = false) Boolean enStock) {
        return ResponseEntity.ok(productoService.listarTodos(categoriaId, precioMin, precioMax, enStock));
    }

    /**
     * Busca productos por nombre (contiene, case-insensitive).
     *
     * @param q texto a buscar en el nombre del producto
     * @return 200 OK con la lista de productos que coinciden, o 400 si {@code q} está vacío
     */
    @GetMapping("/buscar")
    @Operation(summary = "Búsqueda textual de productos por nombre")
    public ResponseEntity<List<ProductoResponse>> buscar(
            @Parameter(description = "Texto a buscar en el nombre", required = true)
            @RequestParam String q) {
        if (q == null || q.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productoService.buscarPorNombre(q));
    }

    /**
     * Lista productos ordenados por un campo y dirección específicos.
     *
     * @param campo campo de ordenamiento: {@code nombre}, {@code precio} o {@code stock}
     * @param orden dirección: {@code asc} o {@code desc} (por defecto {@code asc})
     * @return 200 OK con la lista de productos ordenada
     */
    @GetMapping("/ordenados")
    @Operation(summary = "Listar productos ordenados por campo")
    public ResponseEntity<List<ProductoResponse>> listarOrdenados(
            @Parameter(description = "Campo de ordenamiento: nombre, precio, stock")
            @RequestParam(defaultValue = "nombre") String campo,
            @Parameter(description = "Dirección: asc o desc")
            @RequestParam(defaultValue = "asc") String orden) {
        return ResponseEntity.ok(productoService.listarOrdenados(campo, orden));
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id identificador del producto
     * @return 200 OK con el producto, o 404 si no existe
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    /**
     * Crea un nuevo producto.
     *
     * @param request DTO con los datos del producto a crear
     * @return 201 Created con el producto creado y header Location
     */
    @PostMapping
    @Operation(summary = "Crear nuevo producto")
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse creado = productoService.crear(request);
        URI location = URI.create("/api/productos/" + creado.id());
        return ResponseEntity.created(location).body(creado);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id      identificador del producto a actualizar
     * @param request DTO con los nuevos datos
     * @return 200 OK con el producto actualizado, o 404 si no existe
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.actualizar(id, request));
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id identificador del producto a eliminar
     * @return 204 No Content si se eliminó, 404 si no existe
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
