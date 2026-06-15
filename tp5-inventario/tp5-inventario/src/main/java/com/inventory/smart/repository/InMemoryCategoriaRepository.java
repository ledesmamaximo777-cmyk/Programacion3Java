package com.inventory.smart.repository;

import com.inventory.smart.model.Categoria;
import org.springframework.stereotype.Repository;

/**
 * Implementación en memoria del repositorio de categorías.
 *
 * <p>Extiende {@link GenericInMemoryRepository} heredando el comportamiento CRUD completo.
 * No requiere queries adicionales ya que el dominio de categorías solo necesita
 * las operaciones básicas definidas en {@link IGenericRepository}.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@Repository
public class InMemoryCategoriaRepository
        extends GenericInMemoryRepository<Categoria, Long>
        implements CategoriaRepository {

    /**
     * {@inheritDoc}
     *
     * <p>Asigna un nuevo ID si la categoría no tiene uno, y persiste en el mapa.
     * Complejidad: O(1) amortizado.</p>
     */
    @Override
    public Categoria save(Categoria categoria) {
        if (categoria.getId() == null) {
            categoria.setId(idGenerator.getAndIncrement());
        }
        dataStore.put(categoria.getId(), categoria);
        return categoria;
    }
}
