package com.inventory.smart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para la creación y actualización de productos.
 *
 * <p>Record inmutable que encapsula los datos enviados por el cliente
 * al crear o modificar un producto. Las anotaciones de validación de
 * Jakarta Bean Validation se aplican automáticamente con {@code @Valid}.</p>
 *
 * @param nombre      nombre del producto (entre 2 y 100 caracteres, obligatorio)
 * @param descripcion descripción opcional del producto (máximo 500 caracteres)
 * @param precio      precio unitario del producto (debe ser &gt;= 0)
 * @param stockInicial stock inicial al crear el producto (debe ser &gt;= 0)
 * @param categoriaId  identificador de la categoría a la que pertenece (obligatorio)
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public record ProductoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
        String descripcion,

        @PositiveOrZero(message = "El precio debe ser >= 0")
        double precio,

        @PositiveOrZero(message = "El stock inicial debe ser >= 0")
        int stockInicial,

        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId
) {}
