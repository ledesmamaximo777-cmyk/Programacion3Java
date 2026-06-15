package com.inventory.smart.repository;

import com.inventory.smart.model.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementación en memoria del repositorio de productos.
 *
 * <p>Extiende {@link GenericInMemoryRepository} para heredar el comportamiento CRUD
 * e implementa {@link ProductoRepository} para cumplir con el contrato de dominio.
 * Las queries adicionales ({@code findByCategoria} y {@code buscarPorNombre}) operan
 * sobre {@code dataStore} directamente mediante {@code Stream.filter()}.</p>
 *
 * <p>La anotación {@code @Repository} permite que Spring gestione este bean
 * e inyecte automáticamente las dependencias mediante constructor en los servicios.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@Repository
public class InMemoryProductoRepository
        extends GenericInMemoryRepository<Producto, Long>
        implements ProductoRepository {

    /**
     * {@inheritDoc}
     *
     * <p>Asigna un nuevo ID si el producto no tiene uno, y persiste en el mapa.
     * Complejidad: O(1) amortizado.</p>
     */
    @Override
    public Producto save(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(idGenerator.getAndIncrement());
        }
        dataStore.put(producto.getId(), producto);
        return producto;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(n) — itera todos los productos para filtrar por categoría.</p>
     */
    @Override
    public List<Producto> findByCategoria(Long categoriaId) {
        return dataStore.values().stream()
                .filter(p -> p.getCategoria().getId().equals(categoriaId))
                .toList();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(n·m) donde n es el número de productos y m la longitud
     * del texto de búsqueda. La comparación es case-insensitive.</p>
     */
    @Override
    public List<Producto> buscarPorNombre(String texto) {
        String lower = texto.toLowerCase();
        return dataStore.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(lower))
                .toList();
    }
}
