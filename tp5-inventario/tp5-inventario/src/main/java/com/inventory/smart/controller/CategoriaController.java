package com.inventory.smart.controller;

import com.inventory.smart.dto.CategoriaRequest;
import com.inventory.smart.dto.CategoriaResponse;
import com.inventory.smart.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller REST para la gestión de categorías de productos.
 *
 * <p>Expone los endpoints CRUD de categorías. Delega toda la lógica de negocio
 * a {@link CategoriaService} y se limita a la conversión HTTP ↔ DTO y
 * la construcción de las respuestas.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías", description = "CRUD de categorías de productos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Constructor con inyección de dependencias (DIP).
     *
     * @param categoriaService servicio de gestión de categorías
     */
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Lista todas las categorías registradas.
     *
     * @return 200 OK con la lista de categorías
     */
    @GetMapping
    @Operation(summary = "Listar todas las categorías")
    public ResponseEntity<List<CategoriaResponse>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id identificador de la categoría
     * @return 200 OK con la categoría, o 404 si no existe
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID")
    public ResponseEntity<CategoriaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.obtenerPorId(id));
    }

    /**
     * Crea una nueva categoría.
     *
     * @param request DTO con los datos de la categoría a crear
     * @return 201 Created con la categoría creada y header Location
     */
    @PostMapping
    @Operation(summary = "Crear nueva categoría")
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse creada = categoriaService.crear(request);
        URI location = URI.create("/api/categorias/" + creada.id());
        return ResponseEntity.created(location).body(creada);
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param id      identificador de la categoría a actualizar
     * @param request DTO con los nuevos datos
     * @return 200 OK con la categoría actualizada, o 404 si no existe
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría")
    public ResponseEntity<CategoriaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.actualizar(id, request));
    }

    /**
     * Elimina una categoría. Retorna 409 si tiene productos asociados.
     *
     * @param id identificador de la categoría a eliminar
     * @return 204 No Content si se eliminó, 404 si no existe, 409 si tiene productos
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
