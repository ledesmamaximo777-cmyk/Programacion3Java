package com.inventory.smart.repository;

import com.inventory.smart.model.Categoria;

/**
 * Interfaz de repositorio específica para la entidad {@link Categoria}.
 *
 * <p>Hereda todas las operaciones CRUD de {@link IGenericRepository}.
 * No agrega queries adicionales ya que el dominio de categorías no requiere
 * búsquedas más allá del CRUD básico (ISP — no se incluyen métodos innecesarios).</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public interface CategoriaRepository extends IGenericRepository<Categoria, Long> {
    // Hereda findAll, findById, save, deleteById, existsById, count
    // Sin métodos adicionales — ISP: solo lo que el dominio necesita
}
