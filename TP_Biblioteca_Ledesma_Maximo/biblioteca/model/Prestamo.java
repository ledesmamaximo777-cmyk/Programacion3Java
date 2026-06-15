package model;
import java.time.LocalDate;
import java.util.Objects;

public class Prestamo {
    private Libro libro;
    private Estudiante estudiante;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(Libro libro, Estudiante estudiante, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.libro = libro;
        this.estudiante = estudiante;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Libro getLibro() { return libro; }
    public Estudiante getEstudiante() { return estudiante; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prestamo)) return false;
        Prestamo p = (Prestamo) o;
        return Objects.equals(libro.getIsbn(), p.libro.getIsbn()) &&
               Objects.equals(estudiante.getLegajo(), p.estudiante.getLegajo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(libro.getIsbn(), estudiante.getLegajo());
    }
}