package com.inventory.smart.service;

import com.inventory.smart.model.Categoria;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.InMemoryCategoriaRepository;
import com.inventory.smart.repository.InMemoryProductoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio que genera reportes de performance para los endpoints del sistema.
 *
 * <p>Mide los tiempos de ejecución reales de las operaciones críticas para
 * 1k, 10k y 100k registros usando {@link System#nanoTime()}, generando
 * un reporte JSON que permite validar empíricamente las complejidades Big O.</p>
 *
 * <p>Este servicio es exclusivamente administrativo y no debe utilizarse
 * en flujos normales de negocio (SRP).</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@Service
public class PerformanceReportService {

    /** Tamaños de dataset a medir. */
    private static final int[] DATASET_SIZES = {1_000, 10_000, 100_000};

    /**
     * Genera el reporte completo de performance con mediciones para todos los endpoints.
     *
     * <p>Popula repositorios temporales con los tamaños de dataset configurados
     * y mide cada operación con {@link System#nanoTime()}.</p>
     *
     * @return mapa JSON-serializable con complejidades teóricas y mediciones empíricas
     */
    public Map<String, Object> generarReporte() {
        Map<String, Object> reporte = new HashMap<>();

        reporte.put("complejidades_teoricas", buildComplejidades());
        reporte.put("mediciones", buildMediciones());
        reporte.put("analisis", buildAnalisis());

        return reporte;
    }

    /**
     * Construye la tabla de complejidades teóricas de cada endpoint.
     *
     * @return lista de mapas con endpoint, operación dominante, Big O y justificación
     */
    private List<Map<String, String>> buildComplejidades() {
        List<Map<String, String>> tabla = new ArrayList<>();

        tabla.add(entry("GET /api/productos",          "Stream.filter() sobre ConcurrentHashMap.values()", "O(n)",       "Itera todos los elementos para aplicar filtros."));
        tabla.add(entry("GET /api/productos/{id}",     "ConcurrentHashMap.get(key)",                       "O(1)",       "Hash table con función de dispersión uniforme. Amortizado O(1)."));
        tabla.add(entry("POST /api/productos",         "ConcurrentHashMap.put(key, value)",                "O(1)",       "Inserción en hash table. Amortizado O(1)."));
        tabla.add(entry("PUT /api/productos/{id}",     "ConcurrentHashMap.put(key, value)",                "O(1)",       "Reemplazo en hash table. Amortizado O(1)."));
        tabla.add(entry("DELETE /api/productos/{id}",  "ConcurrentHashMap.remove(key)",                   "O(1)",       "Eliminación en hash table. Amortizado O(1)."));
        tabla.add(entry("GET /api/productos/buscar",   "Stream.filter() + String.contains()",              "O(n·m)",     "n productos; contains() es O(m) donde m es longitud del texto."));
        tabla.add(entry("GET /api/productos/ordenados","List.sort() (TimSort)",                            "O(n log n)", "TimSort es O(n log n) en caso promedio y peor caso."));
        tabla.add(entry("POST /api/movimientos",       "ConcurrentHashMap.put() + AtomicInteger",         "O(1)",       "Ambas operaciones son O(1)."));
        tabla.add(entry("GET /api/movimientos/producto/{id}", "Stream.filter() sobre lista",              "O(n)",       "Itera todos los movimientos para filtrar por producto."));
        tabla.add(entry("GET /api/alertas/stock-bajo", "Stream.filter() sobre ConcurrentHashMap.values()","O(n)",       "Itera todos los productos evaluando condición de stock."));

        return tabla;
    }

    /**
     * Ejecuta las mediciones empíricas para los diferentes tamaños de dataset.
     *
     * @return mapa con los resultados de medición por operación y tamaño
     */
    private Map<String, Object> buildMediciones() {
        Map<String, Object> mediciones = new HashMap<>();

        for (int size : DATASET_SIZES) {
            Map<String, Long> tiempos = medirOperaciones(size);
            mediciones.put("registros_" + size, tiempos);
        }

        return mediciones;
    }

    /**
     * Mide las operaciones principales para un dataset del tamaño indicado.
     *
     * @param size cantidad de productos a usar en la medición
     * @return mapa operación → tiempo en nanosegundos
     */
    private Map<String, Long> medirOperaciones(int size) {
        InMemoryProductoRepository repo = new InMemoryProductoRepository();
        InMemoryCategoriaRepository catRepo = new InMemoryCategoriaRepository();

        Categoria cat = catRepo.save(new Categoria(null, "Test", "Categoría de prueba"));

        // Poblar dataset
        for (int i = 0; i < size; i++) {
            repo.save(new Producto(null, "Producto " + i, "Desc", 10.0 + i, 100, cat));
        }

        Map<String, Long> tiempos = new HashMap<>();

        // Medir GET /api/productos (findAll + filter)
        long start = System.nanoTime();
        repo.findAll();
        tiempos.put("GET_productos_findAll_ns", System.nanoTime() - start);

        // Medir GET /api/productos/{id} (findById — O(1))
        start = System.nanoTime();
        repo.findById(1L);
        tiempos.put("GET_producto_by_id_ns", System.nanoTime() - start);

        // Medir POST /api/productos (save — O(1))
        Producto nuevo = new Producto(null, "Nuevo", "Desc", 99.9, 10, cat);
        start = System.nanoTime();
        repo.save(nuevo);
        tiempos.put("POST_producto_save_ns", System.nanoTime() - start);

        // Medir GET /api/productos/buscar (buscarPorNombre — O(n·m))
        start = System.nanoTime();
        repo.buscarPorNombre("Producto 5");
        tiempos.put("GET_buscar_por_nombre_ns", System.nanoTime() - start);

        // Medir GET /api/productos/ordenados (sort — O(n log n))
        start = System.nanoTime();
        List<Producto> lista = new ArrayList<>(repo.findAll());
        lista.sort(java.util.Comparator.comparing(Producto::getNombre));
        tiempos.put("GET_ordenados_sort_ns", System.nanoTime() - start);

        // Medir GET /api/alertas/stock-bajo (filter — O(n))
        start = System.nanoTime();
        repo.findAll().stream().filter(p -> p.getStock() < 10).toList();
        tiempos.put("GET_alertas_stock_bajo_ns", System.nanoTime() - start);

        return tiempos;
    }

    /**
     * Construye el análisis de discrepancias entre complejidad teórica y observada.
     *
     * @return lista de observaciones sobre el comportamiento empírico
     */
    private List<String> buildAnalisis() {
        return List.of(
                "El overhead de Stream y las lambdas puede hacer que O(1) parezca más lento que O(n) para datasets pequeños.",
                "String.contains() en búsqueda textual introduce un factor adicional O(m) no siempre visible en mediciones.",
                "La recolección de basura (GC) puede introducir ruido significativo en las mediciones de nanosegundos.",
                "ConcurrentHashMap tiene mayor overhead de memoria que HashMap pero garantiza thread-safety sin bloqueo global.",
                "TimSort es especialmente eficiente cuando los datos tienen subconjuntos ya ordenados (O(n) mejor caso)."
        );
    }

    /**
     * Crea un mapa de una entrada de complejidad para la tabla del reporte.
     *
     * @param endpoint    ruta del endpoint
     * @param operacion   operación dominante
     * @param bigO        complejidad Big O teórica
     * @param descripcion justificación de la complejidad
     * @return mapa con las cuatro claves
     */
    private Map<String, String> entry(String endpoint, String operacion,
                                      String bigO, String descripcion) {
        Map<String, String> map = new HashMap<>();
        map.put("endpoint", endpoint);
        map.put("operacion_dominante", operacion);
        map.put("big_o", bigO);
        map.put("descripcion", descripcion);
        return map;
    }
}
