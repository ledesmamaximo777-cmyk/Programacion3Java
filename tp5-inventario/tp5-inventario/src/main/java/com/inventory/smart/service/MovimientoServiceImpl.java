package com.inventory.smart.service;

import com.inventory.smart.dto.MovimientoRequest;
import com.inventory.smart.dto.MovimientoResponse;
import com.inventory.smart.exception.InsufficientStockException;
import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.MovimientoInventario;
import com.inventory.smart.model.Producto;
import com.inventory.smart.model.TipoMovimiento;
import com.inventory.smart.repository.MovimientoRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de gestión de movimientos de inventario.
 *
 * <p>Orquesta el registro de entradas y salidas de stock, aplicando las
 * reglas de negocio: verificación de stock disponible antes de cada salida
 * y actualización atómica mediante {@code AtomicInteger}.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    /**
     * Constructor con inyección de dependencias (DIP).
     *
     * @param movimientoRepository repositorio de movimientos
     * @param productoRepository   repositorio de productos
     */
    public MovimientoServiceImpl(MovimientoRepository movimientoRepository,
                                 ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) amortizado — verificación de stock + actualización
     * atómica con {@code AtomicInteger.addAndGet()} + persistencia en hash map.</p>
     */
    @Override
    public MovimientoResponse registrar(MovimientoRequest request) {
        Producto producto = productoRepository.findById(request.productoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto con ID " + request.productoId() + " no encontrado."));

        int stockResultante;

        if (request.tipo() == TipoMovimiento.SALIDA) {
            int stockActual = producto.getStock();
            if (stockActual < request.cantidad()) {
                throw new InsufficientStockException(
                        "No se pueden retirar " + request.cantidad() +
                        " unidades. Stock disponible: " + stockActual,
                        producto.getId(),
                        stockActual
                );
            }
            stockResultante = producto.decrementarStock(request.cantidad());
        } else {
            stockResultante = producto.incrementarStock(request.cantidad());
        }

        MovimientoInventario movimiento = new MovimientoInventario(
                null,
                producto.getId(),
                request.tipo(),
                request.cantidad(),
                stockResultante,
                request.motivo(),
                LocalDateTime.now()
        );

        MovimientoInventario guardado = movimientoRepository.save(movimiento);
        return toResponse(guardado);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(n) — filtra todos los movimientos por productoId.</p>
     */
    @Override
    public List<MovimientoResponse> obtenerPorProducto(Long productoId) {
        if (!productoRepository.existsById(productoId)) {
            throw new ResourceNotFoundException(
                    "Producto con ID " + productoId + " no encontrado.");
        }
        return movimientoRepository.findByProductoId(productoId).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Convierte una entidad {@link MovimientoInventario} en su DTO de respuesta.
     *
     * @param movimiento entidad a convertir
     * @return DTO {@link MovimientoResponse} inmutable
     */
    private MovimientoResponse toResponse(MovimientoInventario movimiento) {
        return new MovimientoResponse(
                movimiento.getId(),
                movimiento.getProductoId(),
                movimiento.getTipo(),
                movimiento.getCantidad(),
                movimiento.getStockResultante(),
                movimiento.getMotivo(),
                movimiento.getFecha()
        );
    }
}
