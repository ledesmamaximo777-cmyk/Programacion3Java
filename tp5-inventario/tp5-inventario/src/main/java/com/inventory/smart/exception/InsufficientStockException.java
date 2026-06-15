package com.inventory.smart.exception;

/**
 * Excepción lanzada cuando se intenta retirar más stock del disponible.
 *
 * <p>Mapeada a HTTP 409 Conflict por {@link GlobalExceptionHandler}.
 * Incluye el ID del producto y el stock disponible para enriquecer
 * la respuesta de error al cliente.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public class InsufficientStockException extends RuntimeException {

    /** Identificador del producto con stock insuficiente. */
    private final Long productoId;

    /** Stock disponible al momento de la excepción. */
    private final int stockDisponible;

    /**
     * Crea una nueva excepción de stock insuficiente.
     *
     * @param message        mensaje descriptivo del error
     * @param productoId     identificador del producto afectado
     * @param stockDisponible stock disponible al momento del error
     */
    public InsufficientStockException(String message, Long productoId, int stockDisponible) {
        super(message);
        this.productoId = productoId;
        this.stockDisponible = stockDisponible;
    }

    /**
     * Retorna el identificador del producto con stock insuficiente.
     *
     * @return id del producto
     */
    public Long getProductoId() {
        return productoId;
    }

    /**
     * Retorna el stock disponible al momento en que se lanzó la excepción.
     *
     * @return stock disponible
     */
    public int getStockDisponible() {
        return stockDisponible;
    }
}
