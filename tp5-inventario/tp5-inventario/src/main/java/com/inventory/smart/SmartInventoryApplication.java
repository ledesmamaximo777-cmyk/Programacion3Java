package com.inventory.smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Clase principal de la aplicación Smart Inventory.
 *
 * <p>Punto de entrada del microservicio REST para gestión de inventario inteligente.
 * Habilita el escaneo de {@code @ConfigurationProperties} para la configuración
 * de umbrales de stock.</p>
 *
 * @author Grupo Artatak — Inventario Inteligente
 * @since 1.0
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class SmartInventoryApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(SmartInventoryApplication.class, args);
    }
}
