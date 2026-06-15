package com.inventory.smart.service;

import com.inventory.smart.dto.CategoriaResponse;
import com.inventory.smart.dto.ProductoRequest;
import com.inventory.smart.dto.ProductoResponse;
import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.CategoriaRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Implementación del servicio de gestión de productos del inventario.
 *
 * <p>Orquesta las operaciones CRUD, búsqueda y ordenamiento de productos.
 * Aplica el principio de responsabilidad única: solo gestiona lógica de
 * negocio de productos y delega la persistencia a los repositorios.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    /**
     * Constructor con inyección de dependencias (DIP).
     *
     * @param productoRepository  repositorio de productos
     * @param categoriaRepository repositorio de categorías
     */
    public ProductoServiceImpl(ProductoRepository productoRepository,
                               CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(n) — itera todos los productos aplicando los filtros
     * como predicados en un pipeline de Stream.</p>
     */
    @Override
    public List<ProductoResponse> listarTodos(Long categoriaId, Double precioMin,
                                              Double precioMax, Boolean enStock) {
        Stream<Producto> stream = productoRepository.findAll().stream();

        if (categoriaId != null) {
            stream = stream.filter(p -> p.getCategoria().getId().equals(categoriaId));
        }
        if (precioMin != null) {
            stream = stream.filter(p -> p.getPrecio() >= precioMin);
        }
        if (precioMax != null) {
            stream = stream.filter(p -> p.getPrecio() <= precioMax);
        }
        if (Boolean.TRUE.equals(enStock)) {
            stream = stream.filter(p -> p.getStock() > 0);
        }

        return stream.map(this::toResponse).toList();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) amortizado — búsqueda directa en ConcurrentHashMap.</p>
     */
    @Override
    public ProductoResponse obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto con ID " + id + " no encontrado."));
        return toResponse(producto);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) amortizado — inserción en ConcurrentHashMap.</p>
     */
    @Override
    public ProductoResponse crear(ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría con ID " + request.categoriaId() + " no encontrada."));

        Producto producto = new Producto(
                null,
                request.nombre(),
                request.descripcion(),
                request.precio(),
                request.stockInicial(),
                categoria
        );
        return toResponse(productoRepository.save(producto));
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) amortizado — actualización en ConcurrentHashMap.</p>
     */
    @Override
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto con ID " + id + " no encontrado."));

        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría con ID " + request.categoriaId() + " no encontrada."));

        existente.setNombre(request.nombre());
        existente.setDescripcion(request.descripcion());
        existente.setPrecio(request.precio());
        existente.setCategoria(categoria);

        return toResponse(productoRepository.save(existente));
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(1) amortizado — eliminación en ConcurrentHashMap.</p>
     */
    @Override
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto con ID " + id + " no encontrado.");
        }
        productoRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(n·m) — itera n productos, aplica contains() de longitud m.</p>
     */
    @Override
    public List<ProductoResponse> buscarPorNombre(String texto) {
        return productoRepository.buscarPorNombre(texto).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(n log n) — usa TimSort a través de {@link List#sort(Comparator)}.</p>
     */
    @Override
    public List<ProductoResponse> listarOrdenados(String campo, String orden) {
        List<Producto> productos = new java.util.ArrayList<>(productoRepository.findAll());

        Comparator<Producto> comparator = switch (campo.toLowerCase()) {
            case "precio" -> Comparator.comparingDouble(Producto::getPrecio);
            case "stock"  -> Comparator.comparingInt(Producto::getStock);
            default       -> Comparator.comparing(Producto::getNombre);
        };

        if ("desc".equalsIgnoreCase(orden)) {
            comparator = comparator.reversed();
        }

        productos.sort(comparator);
        return productos.stream().map(this::toResponse).toList();
    }

    /**
     * Convierte una entidad {@link Producto} en su DTO de respuesta.
     *
     * @param producto entidad a convertir
     * @return DTO {@link ProductoResponse} inmutable
     */
    private ProductoResponse toResponse(Producto producto) {
        CategoriaResponse catResponse = new CategoriaResponse(
                producto.getCategoria().getId(),
                producto.getCategoria().getNombre(),
                producto.getCategoria().getDescripcion()
        );
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                catResponse
        );
    }
}
