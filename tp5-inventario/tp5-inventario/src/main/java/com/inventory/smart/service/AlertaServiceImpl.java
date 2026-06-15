package com.inventory.smart.service;

import com.inventory.smart.config.StockConfig;
import com.inventory.smart.dto.AlertaStockResponse;
import com.inventory.smart.model.NivelAlerta;
import com.inventory.smart.model.Producto;
import com.inventory.smart.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de alertas de stock bajo.
 *
 * <p>Evalúa el stock de todos los productos contra los umbrales configurados
 * en {@link StockConfig} (leídos de {@code application.yml}).
 * El uso de {@link StockConfig} permite modificar los umbrales sin recompilar
 * el código, aplicando el principio Open/Closed (OCP) mediante el patrón Strategy.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@Service
public class AlertaServiceImpl implements AlertaService {

    private final ProductoRepository productoRepository;
    private final StockConfig stockConfig;

    /**
     * Constructor con inyección de dependencias (DIP).
     *
     * @param productoRepository repositorio de productos
     * @param stockConfig        configuración de umbrales de stock
     */
    public AlertaServiceImpl(ProductoRepository productoRepository,
                             StockConfig stockConfig) {
        this.productoRepository = productoRepository;
        this.stockConfig = stockConfig;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Complejidad: O(n) — itera todos los productos evaluando la condición de stock.</p>
     */
    @Override
    public List<AlertaStockResponse> obtenerProductosConStockBajo() {
        return productoRepository.findAll().stream()
                .filter(p -> p.getStock() < stockConfig.minimo())
                .map(this::toAlertaResponse)
                .toList();
    }

    /**
     * Determina el nivel de alerta de un producto y genera el DTO de respuesta.
     *
     * @param producto producto a evaluar
     * @return DTO con el nivel de alerta determinado
     */
    private AlertaStockResponse toAlertaResponse(Producto producto) {
        NivelAlerta nivel = producto.getStock() < stockConfig.critico()
                ? NivelAlerta.CRITICO
                : NivelAlerta.BAJO;

        return new AlertaStockResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getStock(),
                stockConfig.minimo(),
                stockConfig.critico(),
                nivel
        );
    }
}
