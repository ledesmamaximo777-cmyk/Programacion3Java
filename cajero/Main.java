/**
 * TRABAJO PRÁCTICO Nº 1.2 - PROGRAMACIÓN III
 * Alumno: Ledesma Máximo Nicolás
 * * Punto de entrada para el Simulador de Cajero Automático.
 */
import model.*;
import service.CajeroService;
import exception.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CajeroService servicio = new CajeroService();

        System.out.println("=========================================================");
        System.out.println("   SIMULADOR DE CAJERO AUTOMÁTICO - UNLaR");
        System.out.println("   Alumno: Ledesma Máximo Nicolás");
        System.out.println("=========================================================");

        // 1-CARGA DE DATOS: 3 cuentas bancarias
        CuentaBancaria c1 = new CuentaBancaria("001", "Ledesma Máximo Nicolás", 50000.0);
        CuentaBancaria c2 = new CuentaBancaria("002", "Juan Pérez", 5000.0);
        CuentaBancaria c3 = new CuentaBancaria("003", "Ana Ortiz", 12000.0);

        // 2-SIMULACIÓN DE OPERACIONES (Pruebas automáticas)
        System.out.println("--- Ejecutando transacciones de prueba ---");
        try {
            servicio.depositar(c1, 10000);
            servicio.extraer(c1, 5000);
            servicio.extraer(c1, 2000);
            
            // Probar límites y excepciones
            System.out.println("Intentando extraer más de $10.000 (Límite):");
            servicio.extraer(c1, 15000); 
        } catch (Exception e) {
            System.out.println("EXCEPCIÓN CAPTURADA: " + e.getMessage());
        }

        try {
            System.out.println("Intentando extraer de cuenta con saldo insuficiente:");
            servicio.extraer(c2, 10000);
        } catch (Exception e) {
            System.out.println("EXCEPCIÓN CAPTURADA: " + e.getMessage());
        }

        // 3. MOSTRAR HISTORIAL FORMATEADO
        System.out.println("--- Historial de Transacciones (Cuenta 001) ---");
        c1.getHistorialTransacciones().forEach(System.out::println);
        
        System.out.println("[Simulación terminada. Revise los logs arriba.]");
    }
}