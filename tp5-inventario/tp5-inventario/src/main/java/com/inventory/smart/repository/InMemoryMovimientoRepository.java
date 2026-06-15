package com.inventory.smart.repository;

import com.inventory.smart.model.MovimientoInventario;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementación en memoria del repositorio de movimientos de inventario.
 *
 * <p>Extiende {@link GenericInMemoryRepository} heredando el comportamiento CRUD
 * e implementa {@link MovimientoRepository} para agregar el filtrado por producto.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@Repository
public class InMemoryMovimientoRepository
        extends GenericInMemoryRepository<MovimientoInventario, Long>
        implements MovimientoRepository {

    /**
     * {@inheritDoc}
     *
     * <p>Asigna un nuevo ID si el movimiento no tiene uno, y persiste en el mapa.
     * Complejidad: O(1) amortizado.</p>
     */
    @Override
    public MovimientoInventario save(MovimientoInventario movimiento) {
        if (movimiento.getId() == null) {
            movimiento.setId(idGenerator.getAndIncrement());
        }
        dataStore.put(movimiento.getId(), movimiento);
        return movimiento;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(n) — itera todos los movimientos para filtrar por productoId.</p>
     */
    @Override
    public List<MovimientoInventario> findByProductoId(Long productoId) {
        return dataStore.values().stream()
                .filter(m -> m.getProductoId().equals(productoId))
                .toList();
    }
}
