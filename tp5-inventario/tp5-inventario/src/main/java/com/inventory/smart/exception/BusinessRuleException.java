package com.inventory.smart.exception;

/**
 * Excepción lanzada cuando se viola una regla de negocio del sistema.
 *
 * <p>Mapeada a HTTP 409 Conflict por {@link GlobalExceptionHandler}.
 * Ejemplos de uso: intentar eliminar una categoría que tiene productos asociados,
 * o crear un producto con datos que violan las reglas de integridad del dominio.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public class BusinessRuleException extends RuntimeException {

    /**
     * Crea una nueva excepción de regla de negocio con el mensaje descriptivo dado.
     *
     * @param message mensaje que describe qué regla de negocio fue violada
     */
    public BusinessRuleException(String message) {
        super(message);
    }
}
