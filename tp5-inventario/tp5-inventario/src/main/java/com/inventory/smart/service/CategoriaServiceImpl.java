package com.inventory.smart.service;

import com.inventory.smart.dto.CategoriaRequest;
import com.inventory.smart.dto.CategoriaResponse;
import com.inventory.smart.exception.BusinessRuleException;
import com.inventory.smart.exception.ResourceNotFoundException;
import com.inventory.smart.model.Categoria;
import com.inventory.smart.repository.CategoriaRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de gestión de categorías.
 *
 * <p>Orquesta las operaciones CRUD sobre categorías aplicando las reglas de negocio
 * del dominio. Aplica el principio de responsabilidad única: esta clase solo gestiona
 * la lógica de negocio de categorías y delega la persistencia al repositorio.</p>
 *
 * <p>Las dependencias se inyectan por constructor (DIP), sin usar {@code @Autowired}
 * en campos.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    /**
     * Constructor con inyección de dependencias (DIP — Dependency Inversion Principle).
     *
     * @param categoriaRepository repositorio de categorías
     * @param productoRepository  repositorio de productos (para validar asociaciones)
     */
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository,
                                ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoriaResponse> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoriaResponse obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría con ID " + id + " no encontrada."));
        return toResponse(categoria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoriaResponse crear(CategoriaRequest request) {
        Categoria categoria = new Categoria(null, request.nombre(), request.descripcion());
        Categoria guardada = categoriaRepository.save(categoria);
        return toResponse(guardada);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría con ID " + id + " no encontrada."));
        existente.setNombre(request.nombre());
        existente.setDescripcion(request.descripcion());
        return toResponse(categoriaRepository.save(existente));
    }

    /**
     * {@inheritDoc}
     *
     * <p>Valida que la categoría no tenga productos asociados antes de eliminar.
     * Si los tiene, lanza {@link BusinessRuleException} con HTTP 409.</p>
     */
    @Override
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría con ID " + id + " no encontrada.");
        }
        boolean tieneProductos = !productoRepository.findByCategoria(id).isEmpty();
        if (tieneProductos) {
            throw new BusinessRuleException(
                    "No se puede eliminar la categoría con ID " + id +
                    " porque tiene productos asociados.");
        }
        categoriaRepository.deleteById(id);
    }

    /**
     * Convierte una entidad {@link Categoria} en su DTO de respuesta.
     *
     * @param categoria entidad a convertir
     * @return DTO {@link CategoriaResponse} inmutable
     */
    private CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}
