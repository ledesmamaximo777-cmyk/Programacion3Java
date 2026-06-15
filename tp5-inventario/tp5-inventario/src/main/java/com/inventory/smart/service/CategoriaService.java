package com.inventory.smart.service;

import com.inventory.smart.dto.CategoriaRequest;
import com.inventory.smart.dto.CategoriaResponse;
import com.inventory.smart.exception.BusinessRuleException;
import com.inventory.smart.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de categorías de productos.
 *
 * <p>Define el contrato de operaciones CRUD sobre categorías. Las implementaciones
 * deben aplicar las reglas de negocio, como impedir la eliminación de categorías
 * con productos asociados.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public interface CategoriaService {

    /**
     * Retorna todas las categorías registradas en el sistema.
     *
     * @return lista de respuestas DTO de todas las categorías; vacía si no hay ninguna
     */
    List<CategoriaResponse> listarTodas();

    /**
     * Busca una categoría por su identificador único.
     *
     * @param id identificador de la categoría
     * @return DTO de respuesta con los datos de la categoría
     * @throws ResourceNotFoundException si no existe categoría con el ID dado
     */
    CategoriaResponse obtenerPorId(Long id);

    /**
     * Crea una nueva categoría con los datos provistos.
     *
     * @param request DTO con los datos de la categoría a crear (validado con {@code @Valid})
     * @return DTO de respuesta con los datos de la categoría creada, incluyendo su ID asignado
     */
    CategoriaResponse crear(CategoriaRequest request);

    /**
     * Actualiza los datos de una categoría existente.
     *
     * @param id      identificador de la categoría a actualizar
     * @param request DTO con los nuevos datos de la categoría
     * @return DTO de respuesta con los datos actualizados
     * @throws ResourceNotFoundException si no existe categoría con el ID dado
     */
    CategoriaResponse actualizar(Long id, CategoriaRequest request);

    /**
     * Elimina una categoría del sistema.
     *
     * <p>No se puede eliminar una categoría que tenga productos asociados.</p>
     *
     * @param id identificador de la categoría a eliminar
     * @throws ResourceNotFoundException si no existe categoría con el ID dado
     * @throws BusinessRuleException     si la categoría tiene productos asociados
     */
    void eliminar(Long id);
}
