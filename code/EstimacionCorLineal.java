/******************************************************************
 * Programa:        EstimacionCorLineal.java
 * Autor:           Axel Olmedo Blaco
 * Fecha:           2025-11-25
 * Descripción:     Calcula sumas, parámetros de regresión lineal
 *                  (B0, B1), coeficiente de correlación R y la
 *                  predicción Yk para un Xk dado.
 ******************************************************************/

import java.util.Locale;

public class EstimacionCorLineal {

    // Sumatorias
    private double dblSumX;  /** ΣX — suma de los valores X */
    private double dblSumY; /** ΣY — suma de los valores Y */
    private double dblSumXY;/** ΣXY — suma de productos X*Y */
    private double dblSumXX;/** ΣX² — suma de X al cuadrado */
    private double dblSumYY;/** ΣY² — suma de Y al cuadrado */

    // Promedios
    private double dblAvgX; /** Promedio de X */
    private double dblAvgY; /** Promedio de Y */

    // Cantidad de datos
    private int intN;  /** Número de pares válidos (X,Y) */

    // Parámetros de regresión y correlación
    private double dblB1;     /** Pendiente B1 de la recta de regresión */
    private double dblRXY;    /** Coeficiente de correlación Rxy */
    private double dblB0;     /** Intercepto B0 */
    private double dblK;      // (no usado en este ejemplo, se mantiene por el diagrama)
    private double dblXk;      /** Valor Xk para calcular una predicción */
    private double dblYk;      /** Predicción Yk = B0 + B1 * Xk */
    private double dblR2;     /** Coeficiente de determinación R² */

    /* -------- Sumas básicas -------- */

    /**
     * Calcula y guarda SumX a partir de un arreglo de String.
     */
    public double sumX(String[] datalist) {
        double[] x = toDoubleArray(datalist);
        dblSumX = sum(x);
        intN = x.length;
        dblAvgX = intN > 0 ? dblSumX / intN : 0.0;
        return dblSumX;
    }

    /**
     * Calcula y guarda SumY y AvgY.
     */
    public double sumY(String[] datalist) {
        double[] y = toDoubleArray(datalist);
        dblSumY = sum(y);
        dblAvgY = y.length > 0 ? dblSumY / y.length : 0.0;
        return dblSumY;
    }

    /**
     * Calcula la sumatoria de productos X*Y y actualiza N.
     */
    public double sumXY(String[] datalistX, String[] datalistY) {
        double[] x = toDoubleArray(datalistX);
        double[] y = toDoubleArray(datalistY);
        int n = Math.min(x.length, y.length);
        double acc = 0.0;
        for (int i = 0; i < n; i++) {
            acc += x[i] * y[i];
        }
        dblSumXY = acc;
        intN = n;
        return dblSumXY;
    }

    /** Sum XX = Σ(X^2). */
    public double sumXX(String[] datalist) {
        double[] x = toDoubleArray(datalist);
        double acc = 0.0;
        for (double v : x) acc += v * v;
        dblSumXX = acc;
        return dblSumXX;
    }

    /** Sum YY = Σ(Y^2). */
    public double sumYY(String[] datalist) {
        double[] y = toDoubleArray(datalist);
        double acc = 0.0;
        for (double v : y) acc += v * v;
        dblSumYY = acc;
        return dblSumYY;
    }

    /** Calcula y devuelve el promedio de X (también lo guarda). */
    public double getAvgX(String[] datalist) {
        double[] x = toDoubleArray(datalist);
        dblAvgX = x.length > 0 ? sum(x) / x.length : 0.0;
        return dblAvgX;
    }

    /** Calcula y devuelve el promedio de Y (también lo guarda). */
    public double getAvgY(String[] datalist) {
        double[] y = toDoubleArray(datalist);
        dblAvgY = y.length > 0 ? sum(y) / y.length : 0.0;
        return dblAvgY;
    }

    /* -------- Parámetros y métricas -------- */

    /**
     * Calcula la pendiente B1 de la recta de regresión:
     * B1 = (N*SumXY - SumX*SumY) / (N*SumXX - (SumX)^2)
     */
    public double getB1() {
        double numerator = intN * dblSumXY - dblSumX * dblSumY;
        double denominator = intN * dblSumXX - (dblSumX * dblSumX);
        dblB1 = (denominator == 0.0) ? 0.0 : (numerator / denominator);
        return dblB1;
    }

    /**
     * Calcula el intercepto B0:
     * B0 = AvgY - B1 * AvgX
     */
    public double getB0() {
        dblB0 = dblAvgY - getB1() * dblAvgX;
        return dblB0;
    }

    /**
     * Calcula el coeficiente de correlación de Pearson R.
     */
    public double getRXY() {
        double numerator = intN * dblSumXY - dblSumX * dblSumY;
        double denomLeft  = intN * dblSumXX - (dblSumX * dblSumX);
        double denomRight = intN * dblSumYY - (dblSumY * dblSumY);
        double denominator = Math.sqrt(Math.max(denomLeft, 0.0) * Math.max(denomRight, 0.0));
        dblRXY = (denominator == 0.0) ? 0.0 : (numerator / denominator);
        return dblRXY;
    }

    /**
     * Calcula y devuelve R² = R * R,
     * usando el último valor de RXY calculado.
     */
    public double getR2() {
        dblR2 = dblRXY * dblRXY;
        return dblR2;
    }

    /**
     * Calcula Yk = B0 + B1 * Xk.
     * Se asume que ya se llamaron getB0() y getB1().
     */
    public double getYk() {
        dblYk = dblB0 + dblB1 * dblXk;
        return dblYk;
    }

    /**
     * Magnitud de la correlación (|R|) – se deja por si se requiere.
     */
    public double getR() {
        return Math.abs(dblRXY);
    }

    /* -------- Setters de apoyo -------- */

    public void setXk(double xk) {
        this.dblXk = xk;
    }

    public void setK(double k) {
        this.dblK = k;
    }

    /* -------- Utilidades internas -------- */

    /**
     * Convierte un arreglo de String a double, tolerando espacios y comas decimales.
     */
    private double[] toDoubleArray(String[] in) {
        if (in == null) return new double[0];
        double[] out = new double[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = parseDoubleSafe(in[i]);
        }
        return out;
    }

    private double parseDoubleSafe(String s) {
        if (s == null) return 0.0;
        String t = s.trim().replace(",", ".");
        try {
            return Double.parseDouble(t);
        } catch (NumberFormatException ex) {
            return 0.0; // si no se puede parsear, se toma 0
        }
    }

    private double sum(double[] arr) {
        double acc = 0.0;
        for (double v : arr) acc += v;
        return acc;
    }

    /**
     * Devuelve un reporte de texto con todas las métricas calculadas.
     * Nota: ya NO se imprime Xk, sólo Yk y R² además de las sumas.
     */
    @Override
    public String toString() {
        return String.format(Locale.US,
            "N=%d%n" +
            "SumX=%.6f%n" +
            "SumY=%.6f%n" +
            "SumXY=%.6f%n" +
            "SumXX=%.6f%n" +
            "SumYY=%.6f%n" +
            "AvgX=%.6f%n" +
            "AvgY=%.6f%n" +
            "B1=%.6f%n" +
            "B0=%.6f%n" +
            "RXY=%.6f%n" +
            "R2=%.6f%n" +   // <-- aquí va R*R
            "Yk=%.6f%n",
            intN, dblSumX, dblSumY, dblSumXY, dblSumXX, dblSumYY,
            dblAvgX, dblAvgY, dblB1, dblB0, dblRXY, dblR2, dblYk);
    }
}



