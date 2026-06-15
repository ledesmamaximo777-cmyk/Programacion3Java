package com.inventory.smart.controller;

import com.inventory.smart.dto.AlertaStockResponse;
import com.inventory.smart.service.AlertaService;
import com.inventory.smart.service.PerformanceReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para alertas de stock y reporte de performance administrativo.
 *
 * <p>Expone el endpoint de alertas de stock bajo y el endpoint administrativo
 * de performance. Delega la lógica a {@link AlertaService} y
 * {@link PerformanceReportService} respectivamente.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@RestController
@Tag(name = "Alertas y Admin", description = "Alertas de stock y reporte de performance")
public class AlertaController {

    private final AlertaService alertaService;
    private final PerformanceReportService performanceReportService;

    /**
     * Constructor con inyección de dependencias (DIP).
     *
     * @param alertaService           servicio de detección de alertas
     * @param performanceReportService servicio de reporte de performance
     */
    public AlertaController(AlertaService alertaService,
                            PerformanceReportService performanceReportService) {
        this.alertaService = alertaService;
        this.performanceReportService = performanceReportService;
    }

    /**
     * Retorna todos los productos con stock por debajo del umbral mínimo configurado.
     *
     * @return 200 OK con la lista de alertas de stock (BAJO o CRITICO)
     */
    @GetMapping("/api/alertas/stock-bajo")
    @Operation(summary = "Listar productos con stock bajo o crítico")
    public ResponseEntity<List<AlertaStockResponse>> obtenerStockBajo() {
        return ResponseEntity.ok(alertaService.obtenerProductosConStockBajo());
    }

    /**
     * Genera el reporte de performance con mediciones empíricas y complejidades teóricas.
     *
     * @return 200 OK con el reporte JSON completo
     */
    @GetMapping("/api/admin/performance-report")
    @Operation(summary = "Reporte de performance con complejidades Big O y mediciones empíricas")
    public ResponseEntity<Map<String, Object>> performanceReport() {
        return ResponseEntity.ok(performanceReportService.generarReporte());
    }
}
