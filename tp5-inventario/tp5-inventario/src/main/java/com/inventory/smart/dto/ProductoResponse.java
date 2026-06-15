package com.inventory.smart.dto;

/**
 * DTO de salida para representar un producto en las respuestas HTTP.
 *
 * <p>Record inmutable que expone los datos de un producto al cliente,
 * incluyendo la información de su categoría embebida. Nunca se expone
 * la entidad de dominio {@code Producto} directamente.</p>
 *
 * @param id          identificador único del producto
 * @param nombre      nombre del producto
 * @param descripcion descripción del producto
 * @param precio      precio unitario
 * @param stock       stock actual
 * @param categoria   información resumida de la categoría
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public record ProductoResponse(
        Long id,
        String nombre,
        String descripcion,
        double precio,
        int stock,
        CategoriaResponse categoria
) {}
