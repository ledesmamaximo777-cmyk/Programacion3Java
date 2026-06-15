package ui;
import service.BibliotecaService;
import java.util.Scanner;

public class ConsolaUI {
    private BibliotecaService servicio;
    private Scanner scanner = new Scanner(System.in);

    public ConsolaUI(BibliotecaService s) { this.servicio = s; }

    public void iniciar() {
        System.out.println("Sistema listo para operar.");
    }
}