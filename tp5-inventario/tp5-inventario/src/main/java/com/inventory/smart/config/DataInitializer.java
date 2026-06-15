package com.inventory.smart.config;

import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.CategoriaRepository;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializador de datos de ejemplo para el sistema de inventario.
 *
 * <p>Se ejecuta automáticamente al arrancar la aplicación y carga categorías
 * y productos de muestra en el almacenamiento en memoria, facilitando
 * las pruebas y la demostración del sistema.</p>
 *
 * <p>Implementa {@link CommandLineRunner} para integrarse con el ciclo
 * de vida de Spring Boot sin acoplar la inicialización a ningún controller
 * ni servicio (SRP).</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    /**
     * Constructor con inyección de dependencias por constructor (DIP).
     *
     * @param categoriaRepository repositorio de categorías
     * @param productoRepository  repositorio de productos
     */
    public DataInitializer(CategoriaRepository categoriaRepository,
                           ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Carga los datos iniciales al arrancar la aplicación.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    @Override
    public void run(String... args) {
        // Categorías de ejemplo
        Categoria electronica = categoriaRepository.save(new Categoria(null, "Electrónicos", "Dispositivos y equipos electrónicos"));
        Categoria alimentos   = categoriaRepository.save(new Categoria(null, "Alimentos", "Productos alimenticios y bebidas"));
        Categoria limpieza    = categoriaRepository.save(new Categoria(null, "Limpieza", "Artículos de limpieza e higiene"));

        // Productos de ejemplo
        productoRepository.save(new Producto(null, "Notebook Dell XPS 15", "Laptop de alto rendimiento", 1599.99, 25, electronica));
        productoRepository.save(new Producto(null, "Monitor LG 27\"", "Monitor 4K IPS", 499.99, 8, electronica));
        productoRepository.save(new Producto(null, "Teclado Mecánico", "Teclado RGB Switch Blue", 89.99, 2, electronica));
        productoRepository.save(new Producto(null, "Aceite de Oliva 500ml", "Aceite extra virgen", 12.50, 50, alimentos));
        productoRepository.save(new Producto(null, "Arroz Largo Fino 1kg", "Arroz premium", 3.80, 120, alimentos));
        productoRepository.save(new Producto(null, "Detergente Líquido 1L", "Detergente concentrado", 5.20, 4, limpieza));
        productoRepository.save(new Producto(null, "Lavandina 900ml", "Blanqueador doméstico", 2.90, 1, limpieza));
    }
}
