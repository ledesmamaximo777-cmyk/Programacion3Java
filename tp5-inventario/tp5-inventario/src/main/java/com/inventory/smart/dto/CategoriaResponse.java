package com.inventory.smart.dto;

/**
 * DTO de salida para representar una categoría en las respuestas HTTP.
 *
 * <p>Record inmutable que expone los datos de una categoría al cliente.
 * También se utiliza como objeto embebido dentro de {@link ProductoResponse}.</p>
 *
 * @param id          identificador único de la categoría
 * @param nombre      nombre de la categoría
 * @param descripcion descripción de la categoría
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public record CategoriaResponse(
        Long id,
        String nombre,
        String descripcion
) {}
