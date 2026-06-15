package com.inventory.smart;

import com.inventory.smart.dto.CategoriaRequest;
import com.inventory.smart.dto.MovimientoRequest;
import com.inventory.smart.dto.ProductoRequest;
import com.inventory.smart.model.TipoMovimiento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para los endpoints principales del sistema de inventario.
 *
 * <p>Verifica el flujo completo de cada operación: creación, consulta, actualización
 * y eliminación de categorías y productos, así como el registro de movimientos
 * y la generación de alertas.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class SmartInventoryApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Verifica que la aplicación Spring Boot levanta correctamente.
     */
    @Test
    @DisplayName("El contexto de Spring Boot carga correctamente")
    void contextLoads() {
        // Si el contexto no carga, el test falla automáticamente
    }

    /**
     * Verifica que el endpoint GET /api/categorias retorna 200 OK.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("GET /api/categorias retorna 200 con lista de categorías")
    void listarCategorias_retorna200() throws Exception {
        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Verifica que crear una categoría válida retorna 201 Created.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("POST /api/categorias retorna 201 con categoría creada")
    void crearCategoria_retorna201() throws Exception {
        CategoriaRequest request = new CategoriaRequest("Herramientas", "Herramientas manuales");

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Herramientas"));
    }

    /**
     * Verifica que crear una categoría sin nombre retorna 400 Bad Request.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("POST /api/categorias sin nombre retorna 400")
    void crearCategoria_sinNombre_retorna400() throws Exception {
        CategoriaRequest request = new CategoriaRequest("", null);

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifica que el endpoint GET /api/productos retorna 200 OK.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("GET /api/productos retorna 200 con lista de productos")
    void listarProductos_retorna200() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Verifica que obtener un producto inexistente retorna 404.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("GET /api/productos/{id} con ID inexistente retorna 404")
    void obtenerProducto_inexistente_retorna404() throws Exception {
        mockMvc.perform(get("/api/productos/999999"))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifica que buscar con parámetro vacío retorna 400 Bad Request.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("GET /api/productos/buscar con q vacío retorna 400")
    void buscarProducto_sinParametro_retorna400() throws Exception {
        mockMvc.perform(get("/api/productos/buscar").param("q", ""))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifica que el endpoint de alertas de stock retorna 200 OK.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("GET /api/alertas/stock-bajo retorna 200")
    void alertasStockBajo_retorna200() throws Exception {
        mockMvc.perform(get("/api/alertas/stock-bajo"))
                .andExpect(status().isOk());
    }

    /**
     * Verifica que el reporte de performance retorna 200 con datos.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("GET /api/admin/performance-report retorna 200")
    void performanceReport_retorna200() throws Exception {
        mockMvc.perform(get("/api/admin/performance-report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.complejidades_teoricas").isArray())
                .andExpect(jsonPath("$.mediciones").exists());
    }

    /**
     * Verifica que intentar retirar más stock del disponible retorna 409 Conflict.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("POST /api/movimientos SALIDA con stock insuficiente retorna 409")
    void registrarSalida_stockInsuficiente_retorna409() throws Exception {
        // Producto 3 (Teclado Mecánico) tiene stock=2 en DataInitializer
        MovimientoRequest request = new MovimientoRequest(3L, TipoMovimiento.SALIDA, 500, "Pedido masivo");

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    /**
     * Verifica que eliminar una categoría con productos asociados retorna 409.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("DELETE /api/categorias/{id} con productos asociados retorna 409")
    void eliminarCategoria_conProductos_retorna409() throws Exception {
        // Categoría 1 (Electrónicos) tiene productos en DataInitializer
        mockMvc.perform(delete("/api/categorias/1"))
                .andExpect(status().isConflict());
    }

    /**
     * Verifica el flujo completo: crear categoría → crear producto → registrar movimiento.
     *
     * @throws Exception si ocurre un error al procesar la solicitud mock
     */
    @Test
    @DisplayName("Flujo completo: crear categoría → producto → movimiento")
    void flujoCompleto_crearYMoverStock() throws Exception {
        // 1. Crear categoría
        CategoriaRequest catRequest = new CategoriaRequest("Test Category", "Para tests");
        String catJson = mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(catRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long catId = objectMapper.readTree(catJson).get("id").asLong();

        // 2. Crear producto
        ProductoRequest prodRequest = new ProductoRequest("Producto Test", "Desc", 50.0, 100, catId);
        String prodJson = mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prodRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long prodId = objectMapper.readTree(prodJson).get("id").asLong();

        // 3. Registrar movimiento SALIDA
        MovimientoRequest movRequest = new MovimientoRequest(prodId, TipoMovimiento.SALIDA, 10, "Venta test");
        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.stockResultante").value(90));
    }
}
