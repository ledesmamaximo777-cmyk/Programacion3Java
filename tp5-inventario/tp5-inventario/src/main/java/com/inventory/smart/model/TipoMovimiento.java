package com.inventory.smart.model;

/**
 * Enumeración que define los tipos de movimientos de inventario posibles.
 *
 * <p>Un movimiento de tipo {@link #ENTRADA} incrementa el stock del producto,
 * mientras que uno de tipo {@link #SALIDA} lo decrementa.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public enum TipoMovimiento {

    /**
     * Movimiento de entrada: incrementa el stock del producto.
     * Representa compras, devoluciones u otras incorporaciones al inventario.
     */
    ENTRADA,

    /**
     * Movimiento de salida: decrementa el stock del producto.
     * Representa ventas, mermas u otras extracciones del inventario.
     */
    SALIDA
}
