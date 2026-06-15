package com.inventory.smart.controller;

import com.inventory.smart.dto.MovimientoRequest;
import com.inventory.smart.dto.MovimientoResponse;
import com.inventory.smart.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para la gestión de movimientos de inventario.
 *
 * <p>Expone los endpoints para registrar entradas/salidas de stock y
 * consultar el historial de movimientos por producto.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@RestController
@RequestMapping("/api/movimientos")
@Tag(name = "Movimientos", description = "Registro y consulta de movimientos de inventario")
public class MovimientoController {

    private final MovimientoService movimientoService;

    /**
     * Constructor con inyección de dependencias (DIP).
     *
     * @param movimientoService servicio de gestión de movimientos
     */
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    /**
     * Registra un nuevo movimiento de inventario (entrada o salida).
     *
     * @param request DTO con los datos del movimiento
     * @return 201 Created con el movimiento registrado, o 409 si stock insuficiente
     */
    @PostMapping
    @Operation(summary = "Registrar movimiento de inventario (ENTRADA o SALIDA)")
    public ResponseEntity<MovimientoResponse> registrar(@Valid @RequestBody MovimientoRequest request) {
        MovimientoResponse response = movimientoService.registrar(request);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Retorna el historial de movimientos de un producto específico.
     *
     * @param id identificador del producto
     * @return 200 OK con la lista de movimientos, o 404 si el producto no existe
     */
    @GetMapping("/producto/{id}")
    @Operation(summary = "Historial de movimientos de un producto")
    public ResponseEntity<List<MovimientoResponse>> obtenerPorProducto(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.obtenerPorProducto(id));
    }
}
