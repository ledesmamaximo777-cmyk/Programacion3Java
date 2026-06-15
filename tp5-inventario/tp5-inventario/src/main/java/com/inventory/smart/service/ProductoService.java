package com.inventory.smart.service;

import com.inventory.smart.dto.ProductoRequest;
import com.inventory.smart.dto.ProductoResponse;
import com.inventory.smart.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de productos del inventario.
 *
 * <p>Proporciona operaciones CRUD, búsqueda textual y ordenamiento parametrizado.
 * Aplica las reglas de negocio definidas para la creación y modificación de productos.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public interface ProductoService {

    /**
     * Retorna todos los productos, con filtros opcionales.
     *
     * @param categoriaId filtra por categoría si no es {@code null}
     * @param precioMin   filtra productos con precio &gt;= precioMin si no es {@code null}
     * @param precioMax   filtra productos con precio &lt;= precioMax si no es {@code null}
     * @param enStock     si {@code true}, retorna solo productos con stock &gt; 0
     * @return lista de productos que cumplen los filtros aplicados
     */
    List<ProductoResponse> listarTodos(Long categoriaId, Double precioMin,
                                       Double precioMax, Boolean enStock);

    /**
     * Busca un producto por su identificador único.
     *
     * @param id identificador del producto
     * @return DTO de respuesta con los datos del producto
     * @throws ResourceNotFoundException si no existe producto con el ID dado
     */
    ProductoResponse obtenerPorId(Long id);

    /**
     * Crea un nuevo producto con los datos provistos.
     *
     * @param request DTO con los datos del producto a crear
     * @return DTO de respuesta con los datos del producto creado, incluyendo su ID
     * @throws ResourceNotFoundException si la categoría indicada no existe
     */
    ProductoResponse crear(ProductoRequest request);

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id      identificador del producto a actualizar
     * @param request DTO con los nuevos datos del producto
     * @return DTO de respuesta con los datos actualizados
     * @throws ResourceNotFoundException si no existe producto o categoría con el ID dado
     */
    ProductoResponse actualizar(Long id, ProductoRequest request);

    /**
     * Elimina un producto del sistema.
     *
     * @param id identificador del producto a eliminar
     * @throws ResourceNotFoundException si no existe producto con el ID dado
     */
    void eliminar(Long id);

    /**
     * Busca productos cuyo nombre contenga el texto dado (case-insensitive).
     *
     * @param texto texto a buscar en el nombre del producto
     * @return lista de productos que coinciden con la búsqueda
     */
    List<ProductoResponse> buscarPorNombre(String texto);

    /**
     * Retorna todos los productos ordenados por el campo y dirección especificados.
     *
     * @param campo campo por el cual ordenar: {@code nombre}, {@code precio} o {@code stock}
     * @param orden dirección de ordenamiento: {@code asc} o {@code desc}
     * @return lista de productos ordenados
     */
    List<ProductoResponse> listarOrdenados(String campo, String orden);
}
