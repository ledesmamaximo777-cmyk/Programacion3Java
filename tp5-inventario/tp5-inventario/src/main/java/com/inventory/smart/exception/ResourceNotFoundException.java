package com.inventory.smart.exception;

/**
 * Excepción lanzada cuando una entidad no se encuentra en el repositorio.
 *
 * <p>Mapeada a HTTP 404 Not Found por {@link GlobalExceptionHandler}.
 * Se lanza cuando se intenta acceder a un producto, categoría o movimiento
 * que no existe en el almacenamiento en memoria.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Crea una nueva excepción con el mensaje descriptivo dado.
     *
     * @param message mensaje que describe qué recurso no fue encontrado
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
