package com.inventory.smart.model;

import java.time.LocalDateTime;

/**
 * Entidad que representa un movimiento de inventario (entrada o salida de stock).
 *
 * <p>Cada movimiento queda registrado con el producto afectado, la cantidad,
 * el tipo de operación, el stock resultante y un motivo descriptivo.
 * Permite trazabilidad completa de los cambios en el inventario.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public class MovimientoInventario {

    /** Identificador único del movimiento. */
    private Long id;

    /** Identificador del producto afectado por el movimiento. */
    private Long productoId;

    /** Tipo de movimiento: ENTRADA o SALIDA. */
    private TipoMovimiento tipo;

    /** Cantidad de unidades involucradas en el movimiento. */
    private int cantidad;

    /** Stock del producto resultante tras aplicar el movimiento. */
    private int stockResultante;

    /** Motivo o descripción del movimiento (ej.: "Venta al cliente #1083"). */
    private String motivo;

    /** Fecha y hora en que se registró el movimiento. */
    private LocalDateTime fecha;

    /**
     * Constructor completo para registrar un movimiento de inventario.
     *
     * @param id               identificador único del movimiento
     * @param productoId       identificador del producto afectado
     * @param tipo             tipo de movimiento (ENTRADA o SALIDA)
     * @param cantidad         cantidad de unidades involucradas
     * @param stockResultante  stock del producto tras el movimiento
     * @param motivo           descripción o motivo del movimiento
     * @param fecha            fecha y hora del movimiento
     */
    public MovimientoInventario(Long id, Long productoId, TipoMovimiento tipo,
                                int cantidad, int stockResultante,
                                String motivo, LocalDateTime fecha) {
        this.id = id;
        this.productoId = productoId;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.stockResultante = stockResultante;
        this.motivo = motivo;
        this.fecha = fecha;
    }

    /**
     * Retorna el identificador único del movimiento.
     *
     * @return id del movimiento
     */
    public Long getId() { return id; }

    /**
     * Establece el identificador único del movimiento.
     *
     * @param id nuevo identificador
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Retorna el identificador del producto afectado.
     *
     * @return id del producto
     */
    public Long getProductoId() { return productoId; }

    /**
     * Establece el identificador del producto afectado.
     *
     * @param productoId id del producto
     */
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    /**
     * Retorna el tipo de movimiento.
     *
     * @return tipo de movimiento (ENTRADA o SALIDA)
     */
    public TipoMovimiento getTipo() { return tipo; }

    /**
     * Establece el tipo de movimiento.
     *
     * @param tipo tipo de movimiento
     */
    public void setTipo(TipoMovimiento tipo) { this.tipo = tipo; }

    /**
     * Retorna la cantidad de unidades del movimiento.
     *
     * @return cantidad de unidades
     */
    public int getCantidad() { return cantidad; }

    /**
     * Establece la cantidad de unidades del movimiento.
     *
     * @param cantidad cantidad de unidades
     */
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    /**
     * Retorna el stock resultante tras el movimiento.
     *
     * @return stock resultante
     */
    public int getStockResultante() { return stockResultante; }

    /**
     * Establece el stock resultante tras el movimiento.
     *
     * @param stockResultante nuevo valor de stock resultante
     */
    public void setStockResultante(int stockResultante) { this.stockResultante = stockResultante; }

    /**
     * Retorna el motivo del movimiento.
     *
     * @return motivo o descripción del movimiento
     */
    public String getMotivo() { return motivo; }

    /**
     * Establece el motivo del movimiento.
     *
     * @param motivo motivo o descripción
     */
    public void setMotivo(String motivo) { this.motivo = motivo; }

    /**
     * Retorna la fecha y hora del movimiento.
     *
     * @return fecha y hora del movimiento
     */
    public LocalDateTime getFecha() { return fecha; }

    /**
     * Establece la fecha y hora del movimiento.
     *
     * @param fecha fecha y hora
     */
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
