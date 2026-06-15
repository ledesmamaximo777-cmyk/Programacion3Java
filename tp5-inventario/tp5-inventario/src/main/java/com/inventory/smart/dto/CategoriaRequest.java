package com.inventory.smart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para la creación y actualización de categorías.
 *
 * <p>Record inmutable con validaciones de Jakarta Bean Validation para
 * garantizar la integridad de los datos recibidos del cliente.</p>
 *
 * @param nombre      nombre de la categoría (entre 2 y 50 caracteres, obligatorio)
 * @param descripcion descripción opcional de la categoría (máximo 300 caracteres)
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public record CategoriaRequest(

        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String nombre,

        @Size(max = 300, message = "La descripción no puede superar 300 caracteres")
        String descripcion
) {}
