package com.inventory.smart.model;

/**
 * Entidad que representa una categoría de productos en el inventario.
 *
 * <p>Una categoría agrupa productos relacionados (ej.: Electrónicos, Alimentos, Limpieza).
 * Se relaciona con {@link Producto} mediante composición: un producto <em>tiene una</em>
 * categoría.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public class Categoria {

    /** Identificador único de la categoría. */
    private Long id;

    /** Nombre descriptivo de la categoría. */
    private String nombre;

    /** Descripción opcional de la categoría. */
    private String descripcion;

    /**
     * Constructor que inicializa una categoría con todos sus campos.
     *
     * @param id          identificador único
     * @param nombre      nombre de la categoría
     * @param descripcion descripción opcional
     */
    public Categoria(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Retorna el identificador único de la categoría.
     *
     * @return id de la categoría
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la categoría.
     *
     * @param id nuevo identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el nombre de la categoría.
     *
     * @return nombre de la categoría
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna la descripción de la categoría.
     *
     * @return descripción de la categoría
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la categoría.
     *
     * @param descripcion nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
