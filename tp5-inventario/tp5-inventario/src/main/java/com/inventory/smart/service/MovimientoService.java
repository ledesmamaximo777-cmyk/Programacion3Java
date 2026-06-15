package com.inventory.smart.service;

import com.inventory.smart.dto.MovimientoRequest;
import com.inventory.smart.dto.MovimientoResponse;
import com.inventory.smart.exception.InsufficientStockException;
import com.inventory.smart.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de movimientos de inventario.
 *
 * <p>Gestiona el registro de entradas y salidas de stock, garantizando
 * la atomicidad de las operaciones y las reglas de negocio asociadas.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public interface MovimientoService {

    /**
     * Registra un movimiento de inventario (entrada o salida de stock).
     *
     * <p>Para movimientos de tipo SALIDA, verifica que haya stock suficiente
     * antes de decrementar. La actualización del stock es atómica.</p>
     *
     * @param request DTO con los datos del movimiento a registrar
     * @return DTO de respuesta con el movimiento registrado y el stock resultante
     * @throws ResourceNotFoundException  si el producto no existe
     * @throws InsufficientStockException si es una SALIDA y el stock es insuficiente
     */
    MovimientoResponse registrar(MovimientoRequest request);

    /**
     * Retorna el historial de movimientos de un producto específico.
     *
     * @param productoId identificador del producto
     * @return lista de movimientos del producto, ordenados cronológicamente
     * @throws ResourceNotFoundException si el producto no existe
     */
    List<MovimientoResponse> obtenerPorProducto(Long productoId);
}
