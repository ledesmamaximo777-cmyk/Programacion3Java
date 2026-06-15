package model;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int anio;
    private double precio; // Atributo agregado para el cálculo de multa
    private boolean disponible;

    public Libro() { this.disponible = true; }

    public Libro(String isbn, String titulo, String autor, int anio, double precio) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.precio = precio;
        this.disponible = true;
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public double getPrecio() { return precio; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return "Libro [ISBN=" + isbn + ", Título=" + titulo + ", Precio=" + precio + ", Disponible=" + disponible + "]";
    }
}