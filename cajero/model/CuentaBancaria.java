package model;

import java.util.ArrayList;

public class CuentaBancaria {
    private final String numeroCuenta; // Inmutable según restricción 10
    private double saldo;
    private String titular;
    private boolean activa;
    private ArrayList<String> historialTransacciones;

    public CuentaBancaria(String numeroCuenta, String titular, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
        this.activa = true;
        this.historialTransacciones = new ArrayList<>();
    }

    // Getters y Setters
    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public String getTitular() { return titular; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public ArrayList<String> getHistorialTransacciones() { return historialTransacciones; }

    @Override
    public String toString() {
        return "Cuenta [" + numeroCuenta + "] - Titular: " + titular + " - Saldo: $" + saldo;
    }
}