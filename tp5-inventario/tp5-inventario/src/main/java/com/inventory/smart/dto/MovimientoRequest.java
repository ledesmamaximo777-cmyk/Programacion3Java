package com.inventory.smart.dto;

import com.inventory.smart.model.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para registrar un movimiento de inventario.
 *
 * <p>Encapsula los datos necesarios para registrar una entrada o salida
 * de stock en el inventario. La cantidad debe ser siempre positiva.</p>
 *
 * @param productoId identificador del producto afectado (obligatorio)
 * @param tipo       tipo de movimiento: ENTRADA o SALIDA (obligatorio)
 * @param cantidad   cantidad de unidades involucradas (debe ser &gt; 0)
 * @param motivo     motivo o descripción del movimiento (máximo 255 caracteres)
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public record MovimientoRequest(

        @NotNull(message = "El ID del producto es obligatorio")
        Long productoId,

        @NotNull(message = "El tipo de movimiento es obligatorio")
        TipoMovimiento tipo,

        @Positive(message = "La cantidad debe ser mayor a 0")
        int cantidad,

        @Size(max = 255, message = "El motivo no puede superar 255 caracteres")
        String motivo
) {}
