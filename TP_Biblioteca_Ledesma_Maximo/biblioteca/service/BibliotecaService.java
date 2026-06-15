package service;
import exception.*;
import model.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BibliotecaService {
    private ArrayList<Libro> catalogo = new ArrayList<>();
    private HashMap<String, Estudiante> estudiantes = new HashMap<>();
    private HashSet<Prestamo> prestamosActivos = new HashSet<>();

    public void agregarLibro(Libro l) { catalogo.add(l); }
    public void agregarEstudiante(Estudiante e) { estudiantes.put(e.getLegajo(), e); }

    /**
     * Análisis de la Pila: Cada llamada n suma el 1% y espera a n-1. 
     * Caso base: diasRetraso <= 0.
     */
    public double calcularMulta(int diasRetraso, double valorLibro) {
        if (diasRetraso <= 0) return 0.0;
        if (diasRetraso > 30) diasRetraso = 30;
        return (valorLibro * 0.01) + calcularMulta(diasRetraso - 1, valorLibro);
    }

    public void registrarDevolucion(String isbn, String legajo) throws EstudianteNoEncontradoException {
        Estudiante e = estudiantes.get(legajo);
        if (e == null) throw new EstudianteNoEncontradoException("Estudiante no encontrado");
        
        Prestamo pEncontrado = null;
        for (Prestamo p : prestamosActivos) {
            if (p.getLibro().getIsbn().equals(isbn) && p.getEstudiante().getLegajo().equals(legajo)) {
                pEncontrado = p;
                break;
            }
        }

        if (pEncontrado != null) {
            prestamosActivos.remove(pEncontrado);
            pEncontrado.getLibro().setDisponible(true);
            long dias = ChronoUnit.DAYS.between(pEncontrado.getFechaDevolucion(), LocalDate.now());
            if (dias > 0) {
                double multa = calcularMulta((int)dias, pEncontrado.getLibro().getPrecio());
                System.out.println("Devolución con multa: $" + multa);
            }
        }
    }
    
    public ArrayList<Libro> getCatalogo() { return catalogo; }
}