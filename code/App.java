/******************************************************************
 * Programa:        App.java
 * Autor:           Axel Olmedo Blaco
 * Fecha:           2025-11-25
 * Descripción:     Clase principal del sistema. Se encarga de leer
 *                  el archivo test.txt, interpretar los pares (X,Y)
 *                  contenidos en él, preparar los datos y delegar 
 *                  el cálculo de regresión lineal a la clase Logic.
 *                  Finalmente imprime los resultados en consola y
 *                  los guarda en test_resultados.txt.
 ******************************************************************/

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App 
{

    /**
     * Nombre del archivo que contiene los pares X,Y.
     * Cada línea sigue el formato:
     *      valorX, valorY
     * por ejemplo:
     *      130, 186
     */
    private static final String INPUT_FILE = "test.txt";

    /**
     * Nombre del archivo donde se almacenarán los resultados
     * generados por el cálculo de regresión lineal.
     */
    private static final String OUTPUT_FILE = "test_resultados.txt";


    /**
     * Método principal del programa. Realiza el flujo general:
     * <ol>
     *   <li>Crea instancias de las clases del sistema</li>
     *   <li>Lee los pares X,Y del archivo de entrada</li>
     *   <li>Convierte los valores a arreglos procesables</li>
     *   <li>Configura los datos dentro de Logic</li>
     *   <li>Ejecuta el cálculo de regresión</li>
     *   <li>Imprime y guarda los resultados</li>
     * </ol>
     *
     * @param args Argumentos de consola (no utilizados)
     */
    public static void main(String[] args) 
    {
        // Instanciación de los módulos del diagrama UML
        Input input = new Input();
        Output output = new Output();
        Data data = new Data();
        EstimacionCorLineal est = new EstimacionCorLineal();

        // Logic coordina el proceso de cálculo
        Logic logic = new Logic(input, output, data, est, OUTPUT_FILE);

        try 
        {
            // 1) Leer el contenido completo del archivo test.txt
            String content = input.readData(INPUT_FILE);

            // 2) Convertir las líneas "X, Y" a listas de valores
            ParsedPairs pairs = parseLinesXY(content);

            // --- Depuración: mostrar lo leído ---
            System.out.println("X = " + pairs.x);
            System.out.println("Y = " + pairs.y);

            // 3) Pasar X e Y como cadenas CSV hacia Logic
            logic.setDataX(String.join(",", pairs.x));
            logic.setDataY(String.join(",", pairs.y));

            // 4) Definir Xk (valor para predicción Yk)
            logic.setDataXk("386");

            // 5) Ejecutar el cálculo de regresión lineal
            logic.logic1a();

            // 6) Mostrar resultados en consola
            System.out.println();
            System.out.println("===== RESULTADOS DE LA REGRESIÓN =====");
            System.out.print(est.toString());
            System.out.println("=======================================");

        } 
        catch (IOException ex) 
        {
            System.err.println("Error leyendo " + INPUT_FILE + ": " + ex.getMessage());
        }
    }


    /**
     * Clase auxiliar interna que almacena listas de valores X e Y.
     * Se utiliza para retornar ambos arreglos resultado del
     * procesamiento del archivo test.txt.
     */
    private static class ParsedPairs 
    {
        /** Lista con los valores X del archivo */
        final List<String> x = new ArrayList<>();
        /** Lista con los valores Y del archivo */
        final List<String> y = new ArrayList<>();
    }


    /**
     * Método encargado de interpretar el contenido del archivo
     * test.txt. Cada línea debe tener formato:
     *
     *     valorX, valorY
     *
     * Ejemplo:
     *     130, 186
     *
     * El método separa las líneas, valida que tengan al menos
     * dos valores y los agrega a estructuras separadas (X e Y).
     *
     * @param content El texto completo leído del archivo.
     * @return Objeto ParsedPairs con listas X e Y ya procesadas.
     */
    private static ParsedPairs parseLinesXY(String content) 
    {
        ParsedPairs out = new ParsedPairs();

        if (content == null) 
        {
            return out;
        }

        // Separar líneas (compatible con Windows, Linux y Mac)
        String[] lines = content.split("\\r?\\n");

        for (String line : lines) 
        {
            if (line == null) 
            {
                continue;
            }

            String trimmed = line.trim();
            if (trimmed.isEmpty()) 
            {
                continue; // Línea vacía → ignorar
            }

            // Extraer X e Y separando por coma
            String[] parts = trimmed.split(",");
            if (parts.length < 2) 
            {
                continue; // Línea inválida → ignorar
            }

            // Limpieza y normalización
            String x = parts[0].trim().replace(",", ".");
            String y = parts[1].trim().replace(",", ".");

            // Agregar valores a sus listas
            out.x.add(x);
            out.y.add(y);
        }

        return out;
    }
}



