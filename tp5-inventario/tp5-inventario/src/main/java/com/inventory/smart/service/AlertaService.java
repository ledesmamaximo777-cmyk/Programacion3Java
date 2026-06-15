package com.inventory.smart.service;

import com.inventory.smart.dto.AlertaStockResponse;

import java.util.List;

/**
 * Interfaz de servicio para la detección de alertas de stock bajo.
 *
 * <p>Implementa el patrón Strategy para la evaluación de alertas: la lógica
 * de detección es intercambiable en tiempo de configuración modificando los
 * umbrales en {@code application.yml}, sin alterar el código fuente (OCP).</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public interface AlertaService {

    /**
     * Retorna todos los productos cuyo stock está por debajo del umbral mínimo configurado.
     *
     * <p>Cada elemento incluye el nivel de alerta: {@code BAJO} si el stock está
     * entre el umbral crítico y el mínimo, o {@code CRITICO} si está por debajo
     * del umbral crítico.</p>
     *
     * @return lista de alertas de stock; vacía si todos los productos tienen stock suficiente
     */
    List<AlertaStockResponse> obtenerProductosConStockBajo();
}
