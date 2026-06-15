package com.inventory.smart.repository;

import com.inventory.smart.model.MovimientoInventario;

import java.util.List;

/**
 * Interfaz de repositorio específica para la entidad {@link MovimientoInventario}.
 *
 * <p>Extiende {@link IGenericRepository} con la operación adicional de filtrado
 * por producto, necesaria para obtener el historial de movimientos de un producto.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public interface MovimientoRepository extends IGenericRepository<MovimientoInventario, Long> {

    /**
     * Retorna todos los movimientos de inventario asociados a un producto específico.
     *
     * <p>Complejidad: O(n) — itera todos los movimientos para filtrar por productoId.</p>
     *
     * @param productoId identificador del producto cuyo historial se quiere obtener
     * @return lista de movimientos del producto; vacía si no hay ninguno
     */
    List<MovimientoInventario> findByProductoId(Long productoId);
}
