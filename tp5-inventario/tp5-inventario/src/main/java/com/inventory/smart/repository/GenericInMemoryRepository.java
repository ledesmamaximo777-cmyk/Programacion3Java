package com.inventory.smart.repository;

import com.inventory.smart.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación abstracta de {@link IGenericRepository} usando almacenamiento en memoria.
 *
 * <p>Utiliza {@link ConcurrentHashMap} como estructura de datos subyacente para garantizar
 * thread-safety sin bloquear todo el mapa (lock stripping interno de Java 8+).
 * Esto evita condiciones de carrera o {@code ConcurrentModificationException}
 * en entornos con múltiples requests HTTP concurrentes.</p>
 *
 * <p>El generador de IDs es un {@link AtomicLong} iniciado en 1, lo que garantiza
 * que la asignación de identificadores también sea thread-safe.</p>
 *
 * <p>Las subclases concretas heredan el comportamiento CRUD completo y pueden
 * agregar queries específicas accediendo a {@code dataStore} directamente.</p>
 *
 * @param <T>  tipo de la entidad gestionada
 * @param <ID> tipo del identificador de la entidad (se espera {@link Long})
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public abstract class GenericInMemoryRepository<T, ID> implements IGenericRepository<T, ID> {

    /**
     * Almacén de datos en memoria. Accesible por subclases para implementar
     * queries adicionales. Thread-safe por diseño de {@link ConcurrentHashMap}.
     */
    protected final ConcurrentHashMap<ID, T> dataStore = new ConcurrentHashMap<>();

    /**
     * Generador atómico de IDs. Iniciado en 1 y con incremento monótono creciente.
     * Garantiza unicidad e integridad en contextos concurrentes.
     */
    protected final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(n) — copia todos los valores del {@link ConcurrentHashMap}.</p>
     */
    @Override
    public List<T> findAll() {
        return new ArrayList<>(dataStore.values());
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) amortizado — búsqueda directa en hash table.</p>
     */
    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dataStore.get(id));
    }

    /**
     * {@inheritDoc}
     *
     * <p>La asignación de ID y persistencia deben ser coordinadas por subclases
     * que conozcan el tipo concreto de la entidad. Este método realiza la
     * inserción en el mapa dado el ID ya asignado.</p>
     *
     * <p>Complejidad: O(1) amortizado — inserción en hash table.</p>
     */
    @Override
    public abstract T save(T entity);

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) amortizado — eliminación en hash table.</p>
     *
     * @throws ResourceNotFoundException si no existe ninguna entidad con el ID dado
     */
    @Override
    public void deleteById(ID id) {
        if (!dataStore.containsKey(id)) {
            throw new ResourceNotFoundException("Entidad con ID " + id + " no encontrada.");
        }
        dataStore.remove(id);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) amortizado — verificación de existencia en hash table.</p>
     */
    @Override
    public boolean existsById(ID id) {
        return dataStore.containsKey(id);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) — tamaño del mapa se mantiene internamente.</p>
     */
    @Override
    public long count() {
        return dataStore.size();
    }
}
