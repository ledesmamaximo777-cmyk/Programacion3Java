package com.inventory.smart.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuración de umbrales de stock leída desde {@code application.yml}.
 *
 * <p>Record inmutable que encapsula los valores del prefijo {@code inventario.stock}.
 * Permite que los umbrales de alerta sean configurables sin recompilar el código,
 * aplicando el principio Open/Closed: el comportamiento de las alertas se puede
 * modificar cambiando la configuración externa.</p>
 *
 * <p>Ejemplo de configuración en {@code application.yml}:
 * <pre>
 * inventario:
 *   stock:
 *     minimo: 10
 *     critico: 3
 * </pre>
 * </p>
 *
 * @param minimo  umbral mínimo; si {@code stock < minimo}, alerta BAJO
 * @param critico umbral crítico; si {@code stock < critico}, alerta CRITICO
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@ConfigurationProperties(prefix = "inventario.stock")
public record StockConfig(int minimo, int critico) {}
