package com.inventory.smart.model;

/**
 * Enumeración que define los niveles de alerta de stock de un producto.
 *
 * <p>Los niveles se determinan comparando el stock actual con los umbrales
 * configurados en {@code application.yml} (inventario.stock.minimo y
 * inventario.stock.critico).</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public enum NivelAlerta {

    /** El stock se encuentra dentro de los niveles normales ({@code stock >= minimo}). */
    NORMAL,

    /** El stock está bajo pero no crítico ({@code critico <= stock < minimo}). */
    BAJO,

    /** El stock está en nivel crítico ({@code stock < critico}). */
    CRITICO
}
