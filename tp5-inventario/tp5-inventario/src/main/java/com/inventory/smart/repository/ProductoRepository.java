package com.inventory.smart.repository;

import com.inventory.smart.model.Producto;

import java.util.List;

/**
 * Interfaz de repositorio específica para la entidad {@link Producto}.
 *
 * <p>Extiende {@link IGenericRepository} con las operaciones CRUD heredadas
 * y agrega queries de dominio propias de los productos (ISP aplicado).
 * Sigue el principio de <em>Dependency Inversion</em>: los servicios dependen
 * de esta abstracción, no de la implementación concreta.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public interface ProductoRepository extends IGenericRepository<Producto, Long> {

    /**
     * Retorna todos los productos que pertenecen a una categoría específica.
     *
     * <p>Complejidad: O(n) — itera todos los productos para filtrar por categoría.</p>
     *
     * @param categoriaId identificador de la categoría a filtrar
     * @return lista de productos de la categoría; vacía si no hay ninguno
     */
    List<Producto> findByCategoria(Long categoriaId);

    /**
     * Busca productos cuyo nombre contenga el texto dado (case-insensitive).
     *
     * <p>Complejidad: O(n·m) — itera n productos y aplica {@code String.contains()}
     * de longitud m sobre cada nombre.</p>
     *
     * @param texto texto a buscar dentro del nombre del producto
     * @return lista de productos cuyos nombres contienen el texto; vacía si no hay coincidencias
     */
    List<Producto> buscarPorNombre(String texto);
}
