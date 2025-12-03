/******************************************************************
 * Programa:        Logic.java
 * Autor:           Axel Olmedo Blaco
 * Fecha:           2025-11-25
 * Descripción:     Orquesta el flujo principal: normaliza los datos
 *                  de entrada, invoca los cálculos de regresión
 *                  lineal en EstimacionCorLineal y genera el archivo 
 *                  de salida con los resultados.
 ******************************************************************/

public class Logic 
{
    /* ==========================================================
       =============   VARIABLES DE ENTRADA CRUDA   =============
       ========================================================== */

    /**
     * Cadena con todos los valores X separados por coma,
     * enviada desde App antes de procesarse.
     */
    private String dataX;

    /**
     * Cadena con todos los valores Y separados por coma,
     * enviada desde App antes de procesarse.
     */
    private String dataY;

    /**
     * Cadena que contiene el valor único Xk sobre el cual
     * se calculará la predicción Yk.
     */
    private String dataXk;


    /* ==========================================================
       ===========   ARREGLOS NORMALIZADOS (PROCESADOS)   =======
       ========================================================== */

    /**
     * Arreglo resultante de normalizar los valores X.
     */
    private String[] arrDataX;

    /**
     * Arreglo resultante de normalizar Xk.
     * Normalmente contiene un único valor.
     */
    private String[] arrDataXk;


    /* ==========================================================
       ==================   VARIABLES NUMÉRICAS   ===============
       ========================================================== */

    /**
     * Valor numérico Xk para evaluar la ecuación:
     *        Yk = B0 + B1 * Xk
     */
    private double thisXk;


    /* ==========================================================
       ==================   DEPENDENCIAS UML   ==================
       ========================================================== */

    /** Asociación con el módulo Input (no se usa directamente aquí). */
    private final Input input;

    /** Módulo responsable de escribir el archivo de salida. */
    private final Output output;

    /** Módulo que normaliza cadenas CSV en arreglos limpios. */
    private final Data data;

    /** Motor matemático que realiza los cálculos de regresión. */
    private final EstimacionCorLineal est;

    /** Nombre del archivo final donde se guardarán los resultados. */
    private final String outFile;


    /* ==========================================================
       ======================   CONSTRUCTOR   ====================
       ========================================================== */

    /**
     * Constructor que inyecta todas las dependencias necesarias
     * para ejecutar el flujo completo del sistema.
     *
     * @param input   módulo Input asociado en UML
     * @param output  módulo Output asociado en UML
     * @param data    módulo Data encargado de normalizar valores
     * @param est     clase que realiza cálculos de regresión
     * @param outFile nombre del archivo en el que se guardará el reporte
     */
    public Logic(Input input, Output output, Data data,
                 EstimacionCorLineal est, String outFile) 
    {
        this.input   = input;
        this.output  = output;
        this.data    = data;
        this.est     = est;
        this.outFile = outFile;
    }


    /* ==========================================================
       ========================   MÉTODO CENTRAL   ===============
       ========================================================== */

    /**
     * Ejecuta el flujo completo de la estimación:
     * <ul>
     *     <li>Normaliza valores X e Y</li>
     *     <li>Calcula sumas y promedios</li>
     *     <li>Calcula coeficientes B0, B1</li>
     *     <li>Calcula correlación R, R²</li>
     *     <li>Calcula Yk si se definió Xk</li>
     *     <li>Genera archivo de reporte</li>
     * </ul>
     */
    public void logic1a() 
    {
        try 
        {
            /* ============================
               === 1) Normalizar datos X ===
               ============================ */
            if (dataX != null) 
            {
                arrDataX = data.saveData(dataX);
            }

            /* ============================
               === 2) Normalizar datos Y ===
               ============================ */
            if (dataY != null) 
            {
                String[] arrY = data.saveData(dataY);

                // ======== SUMAS =========
                est.sumX(arrDataX);
                est.sumY(arrY);
                est.sumXY(arrDataX, arrY);
                est.sumXX(arrDataX);
                est.sumYY(arrY);

                // ======== PROMEDIOS ========
                est.getAvgX(arrDataX);
                est.getAvgY(arrY);

                // ======== REGRESIÓN ========
                est.getB1();   // pendiente
                est.getB0();   // intercepto
                est.getRXY();  // correlación
                est.getR2();   // R² = R * R

                /* =============================
                   === 3) SI EXISTE Xk → Yk ===
                   ============================= */
                if (dataXk != null) 
                {
                    // Normalizar Xk (puede venir "386" o "386," etc.)
                    arrDataXk = data.saveData(dataXk);

                    if (arrDataXk.length > 0) 
                    {
                        // Convertir Xk a double
                        thisXk = Double.parseDouble(
                                arrDataXk[0].trim().replace(",", ".")
                        );

                        est.setXk(thisXk);
                        est.getYk(); // Yk = B0 + B1 * Xk
                    }
                }
            }

            /* =======================================
               === 4) Generar archivo de resultados ===
               ======================================= */
            output.writeData(outFile, est.toString());

        } 
        catch (Exception ex) 
        {
            System.err.println("Error en logic1a: " + ex.getMessage());
        }
    }


    /* ==========================================================
       ========================   SETTERS   ======================
       ========================================================== */

    /**
     * Recibe los valores X como cadena CSV.
     * @param dataX valores crudos X separados por coma
     */
    public void setDataX(String dataX) 
    {
        this.dataX = dataX;
    }

    /**
     * Recibe los valores Y como cadena CSV.
     * @param dataY valores crudos Y separados por coma
     */
    public void setDataY(String dataY) 
    {
        this.dataY = dataY;
    }

    /**
     * Define el Xk sobre el cual se quiere calcular Yk.
     * Si no se establece, el sistema omitirá el cálculo de Yk.
     *
     * @param dataXk valor de Xk como cadena
     */
    public void setDataXk(String dataXk) 
    {
        this.dataXk = dataXk;
    }
}






