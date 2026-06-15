package com.inventory.smart.repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica que define el contrato CRUD para todos los repositorios del sistema.
 *
 * <p>Aplica el principio de <em>Interface Segregation</em> (ISP) al proporcionar
 * únicamente las operaciones básicas que todo repositorio debe soportar.
 * Los repositorios específicos extienden esta interfaz para agregar queries
 * adicionales sin violar el principio de responsabilidad única.</p>
 *
 * <p>El uso de genéricos paramétricos permite reutilizar esta interfaz para
 * cualquier tipo de entidad e identificador, aplicando polimorfismo paramétrico.</p>
 *
 * @param <T>  tipo de la entidad gestionada por el repositorio
 * @param <ID> tipo del identificador único de la entidad
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public interface IGenericRepository<T, ID> {

    /**
     * Retorna todas las entidades almacenadas en el repositorio.
     *
     * @return lista inmutable con todas las entidades; nunca {@code null}
     */
    List<T> findAll();

    /**
     * Busca una entidad por su identificador único.
     *
     * @param id identificador de la entidad a buscar
     * @return {@link Optional} con la entidad si existe, o vacío si no se encuentra
     */
    Optional<T> findById(ID id);

    /**
     * Persiste una entidad en el repositorio.
     *
     * <p>Si la entidad no tiene ID asignado, se le asigna uno nuevo automáticamente.
     * Si ya tiene ID, actualiza la entrada existente.</p>
     *
     * @param entity entidad a persistir (no debe ser {@code null})
     * @return la entidad persistida con su ID asignado
     */
    T save(T entity);

    /**
     * Elimina una entidad por su identificador único.
     *
     * @param id identificador de la entidad a eliminar
     * @throws com.inventory.smart.exception.ResourceNotFoundException si no existe
     *         ninguna entidad con el ID dado
     */
    void deleteById(ID id);

    /**
     * Verifica si existe una entidad con el identificador dado.
     *
     * @param id identificador a verificar
     * @return {@code true} si existe la entidad, {@code false} en caso contrario
     */
    boolean existsById(ID id);

    /**
     * Retorna la cantidad total de entidades almacenadas en el repositorio.
     *
     * @return cantidad de entidades; 0 si el repositorio está vacío
     */
    long count();
}
