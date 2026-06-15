package com.inventory.smart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejador centralizado de excepciones para toda la API REST.
 *
 * <p>Implementa el patrón de manejo centralizado de errores mediante
 * {@code @ControllerAdvice}, evitando que los controllers contengan
 * lógica de manejo de errores (SRP). Cada excepción de dominio se mapea
 * al código HTTP correspondiente con un body JSON descriptivo.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de recurso no encontrado → HTTP 404.
     *
     * @param ex excepción lanzada cuando una entidad no existe
     * @return respuesta HTTP 404 con mensaje descriptivo
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Recurso no encontrado");
        body.put("mensaje", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * Maneja excepciones de stock insuficiente → HTTP 409 Conflict.
     *
     * @param ex excepción con información del producto y stock disponible
     * @return respuesta HTTP 409 con detalle del stock disponible
     */
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientStock(InsufficientStockException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Stock insuficiente");
        body.put("mensaje", ex.getMessage());
        body.put("productoId", ex.getProductoId());
        body.put("stockDisponible", ex.getStockDisponible());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    /**
     * Maneja excepciones de regla de negocio violada → HTTP 409 Conflict.
     *
     * @param ex excepción con el mensaje de la regla violada
     * @return respuesta HTTP 409 con mensaje descriptivo
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessRule(BusinessRuleException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Violación de regla de negocio");
        body.put("mensaje", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    /**
     * Maneja excepciones de validación de Bean Validation → HTTP 400 Bad Request.
     *
     * <p>Agrega al body un mapa de campo → mensaje de error para facilitar
     * la corrección por parte del cliente.</p>
     *
     * @param ex excepción con los errores de validación por campo
     * @return respuesta HTTP 400 con mapa de errores por campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Error de validación",
                        (existing, replacement) -> existing
                ));

        Map<String, Object> body = new HashMap<>();
        body.put("error", "Error de validación");
        body.put("campos", errores);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Maneja excepciones genéricas no contempladas → HTTP 500.
     *
     * @param ex excepción inesperada
     * @return respuesta HTTP 500 con mensaje genérico
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Error interno del servidor");
        body.put("mensaje", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
