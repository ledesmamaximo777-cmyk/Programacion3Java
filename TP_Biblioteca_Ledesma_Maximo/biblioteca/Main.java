/**
 * TRABAJO PRÁCTICO Nº 1.1 - PROGRAMACIÓN III
 * Alumno: Ledesma Máximo Nicolás
 * * Punto de entrada del sistema de Biblioteca Universitaria.
 */
import exception.EstudianteNoEncontradoException;
import exception.LibroNoDisponibleException;
import exception.LimitePrestamosExcedidoException;
import model.Estudiante;
import model.Libro;
import service.BibliotecaService;
import ui.ConsolaUI;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        BibliotecaService servicio = new BibliotecaService();

        System.out.println("=========================================================");
        System.out.println("   SISTEMA DE GESTIÓN DE BIBLIOTECA UNIVERSITARIA");
        System.out.println("   Alumno: Ledesma Máximo Nicolás");
        System.out.println("=========================================================");

        // 1. CARGA DE DATOS: 5 libros (con precio) y 3 estudiantes
        Libro l1 = new Libro("978-0-13-110362-7", "El Lenguaje de Programación C", "Kernighan & Ritchie", 1988, 2500.0);
        Libro l2 = new Libro("978-0-13-468599-1", "Clean Code", "Robert C. Martin", 2008, 3200.0);
        Libro l3 = new Libro("978-0-596-51774-8", "Head First Java", "Kathy Sierra", 2005, 1800.0);
        Libro l4 = new Libro("978-1-61729-199-9", "Java 8 in Action", "Raoul-Gabriel Urma", 2014, 2100.0);
        Libro l5 = new Libro("978-0-321-35668-0", "Effective Java", "Joshua Bloch", 2008, 2900.0);

        servicio.agregarLibro(l1);
        servicio.agregarLibro(l2);
        servicio.agregarLibro(l3);
        servicio.agregarLibro(l4);
        servicio.agregarLibro(l5);

        Estudiante e1 = new Estudiante("10001", "Maximo Ledesma", "Ing. en Sistemas", "maxi@unlar.edu.ar");
        Estudiante e2 = new Estudiante("10002", "Carlos Gomez", "Ing. en Sistemas", "carlos@unlar.edu.ar");
        Estudiante e3 = new Estudiante("10003", "Ana Lopez", "Ing. en Sistemas", "ana@unlar.edu.ar");

        servicio.agregarEstudiante(e1);
        servicio.agregarEstudiante(e2);
        servicio.agregarEstudiante(e3);

        // Prueba de Multa Recursiva (15 días)
        System.out.println("--- Prueba de Multa Recursiva (15 días) ---");
        double valorLibro = 2500.0;
        int diasRetraso = 15;
        double multa = servicio.calcularMulta(diasRetraso, valorLibro);
        System.out.printf("Multa por %d días de retraso sobre un libro de $%.2f: $%.2f%n",
                diasRetraso, valorLibro, multa);

        // Inicio de Interfaz
        ConsolaUI ui = new ConsolaUI(servicio);
        ui.iniciar();
    }
}