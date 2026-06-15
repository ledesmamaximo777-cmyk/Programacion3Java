package com.inventory.smart.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Entidad que representa un producto del inventario.
 *
 * <p>Un producto pertenece a una {@link Categoria} mediante composición (tiene-una).
 * El stock se gestiona de forma thread-safe con {@link AtomicInteger}, garantizando
 * atomicidad en operaciones concurrentes de incremento y decremento.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
public class Producto {

    /** Identificador único del producto. */
    private Long id;

    /** Nombre del producto. */
    private String nombre;

    /** Descripción detallada del producto. */
    private String descripcion;

    /** Precio unitario del producto. */
    private double precio;

    /**
     * Stock actual del producto. Se utiliza {@link AtomicInteger} para garantizar
     * operaciones de lectura y escritura thread-safe sin bloqueos explícitos.
     */
    private final AtomicInteger stock;

    /** Categoría a la que pertenece el producto (composición). */
    private Categoria categoria;

    /**
     * Constructor completo para inicializar un producto.
     *
     * @param id          identificador único
     * @param nombre      nombre del producto
     * @param descripcion descripción del producto
     * @param precio      precio unitario (debe ser &gt;= 0)
     * @param stock       cantidad inicial en stock (debe ser &gt;= 0)
     * @param categoria   categoría a la que pertenece el producto
     */
    public Producto(Long id, String nombre, String descripcion,
                    double precio, int stock, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = new AtomicInteger(stock);
        this.categoria = categoria;
    }

    /**
     * Retorna el identificador del producto.
     *
     * @return id del producto
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param id nuevo identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el nombre del producto.
     *
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna la descripción del producto.
     *
     * @return descripción del producto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Retorna el precio unitario del producto.
     *
     * @return precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precio nuevo precio
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Retorna el stock actual del producto de forma thread-safe.
     *
     * @return cantidad actual en stock
     */
    public int getStock() {
        return stock.get();
    }

    /**
     * Incrementa el stock del producto de forma atómica.
     *
     * @param cantidad cantidad a incrementar (debe ser positiva)
     * @return nuevo valor del stock tras el incremento
     */
    public int incrementarStock(int cantidad) {
        return stock.addAndGet(cantidad);
    }

    /**
     * Decrementa el stock del producto de forma atómica.
     *
     * @param cantidad cantidad a decrementar (debe ser positiva)
     * @return nuevo valor del stock tras el decremento
     */
    public int decrementarStock(int cantidad) {
        return stock.addAndGet(-cantidad);
    }

    /**
     * Retorna la categoría a la que pertenece el producto.
     *
     * @return categoría del producto
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del producto.
     *
     * @param categoria nueva categoría
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
