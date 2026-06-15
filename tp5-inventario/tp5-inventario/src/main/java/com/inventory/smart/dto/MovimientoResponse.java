package com.inventory.smart.dto;

import com.inventory.smart.model.TipoMovimiento;
import java.time.LocalDateTime;

/**
 * DTO de salida para representar un movimiento de inventario en las respuestas HTTP.
 *
 * <p>Record inmutable que expone al cliente los datos del movimiento registrado,
 * incluyendo el stock resultante tras la operación y la fecha de registro.</p>
 *
 * @param id              identificador único del movimiento
 * @param productoId      identificador del producto afectado
 * @param tipo            tipo de movimiento (ENTRADA o SALIDA)
 * @param cantidad        cantidad de unidades involucradas
 * @param stockResultante stock del producto tras aplicar el movimiento
 * @param motivo          descripción o motivo del movimiento
 * @param fecha           fecha y hora en que se registró el movimiento
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public record MovimientoResponse(
        Long id,
        Long productoId,
        TipoMovimiento tipo,
        int cantidad,
        int stockResultante,
        String motivo,
        LocalDateTime fecha
) {}
