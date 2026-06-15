package model;

public class Estudiante {
    private String legajo;
    private String nombre;
    private String carrera;
    private String email;

    public Estudiante() {}

    public Estudiante(String legajo, String nombre, String carrera, String email) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.carrera = carrera;
        this.email = email;
    }

    public String getLegajo() { return legajo; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return "Estudiante [Legajo=" + legajo + ", Nombre=" + nombre + "]";
    }
}