package service;

import model.*;
import exception.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CajeroService {

    public void depositar(CuentaBancaria cuenta, double monto) throws CuentaInactivaException {
        validarEstado(cuenta);
        if (monto <= 0) return;
        
        cuenta.setSaldo(cuenta.getSaldo() + monto);
        registrarLog(cuenta, TipoTransaccion.DEPOSITO, monto);
    }

    public void extraer(CuentaBancaria cuenta, double monto) 
            throws SaldoInsuficienteException, LimiteExtraccionExcedidoException, CuentaInactivaException {
        
        validarEstado(cuenta);
        if (monto > 10000) throw new LimiteExtraccionExcedidoException("Monto mayor a $10,000 por operación.");
        if (cuenta.getSaldo() < monto) throw new SaldoInsuficienteException("Saldo insuficiente en cuenta.");

        cuenta.setSaldo(cuenta.getSaldo() - monto);
        registrarLog(cuenta, TipoTransaccion.EXTRACCION, monto);
    }

    private void validarEstado(CuentaBancaria cuenta) throws CuentaInactivaException {
        if (!cuenta.isActiva()) throw new CuentaInactivaException("La cuenta se encuentra inactiva.");
    }

    private void registrarLog(CuentaBancaria cuenta, TipoTransaccion tipo, double monto) {
        StringBuilder sb = new StringBuilder(); // Uso de StringBuilder (Restricción 11)
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        sb.append("[").append(dtf.format(LocalDateTime.now())).append("] ")
          .append(tipo).append(": $").append(String.format("%.2f", monto))
          .append(" | Saldo resultante: $").append(String.format("%.2f", cuenta.getSaldo()));
        
        cuenta.getHistorialTransacciones().add(sb.toString());
    }
}