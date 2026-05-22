package util;
public class FormatoUtil {
    public static String formatearMoneda(double monto) {
        return String.format("$%.2f", monto);
    }
}