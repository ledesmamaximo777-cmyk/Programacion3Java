package com.inventory.smart.dto;

import com.inventory.smart.model.NivelAlerta;

/**
 * DTO de salida para representar una alerta de stock bajo en las respuestas HTTP.
 *
 * <p>Encapsula la información del producto en alerta junto con su nivel
 * de criticidad según los umbrales configurados en {@code application.yml}.</p>
 *
 * @param productoId   identificador del producto en alerta
 * @param nombre       nombre del producto en alerta
 * @param stockActual  stock actual del producto
 * @param stockMinimo  umbral mínimo configurado
 * @param stockCritico umbral crítico configurado
 * @param nivel        nivel de alerta: BAJO o CRITICO
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public record AlertaStockResponse(
        Long productoId,
        String nombre,
        int stockActual,
        int stockMinimo,
        int stockCritico,
        NivelAlerta nivel
) {}
