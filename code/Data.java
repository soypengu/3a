/******************************************************************
 * Programa:        Data.java
 * Autor:           Axel Olmedo Blaco
 * Fecha:           2025-11-25
 * Descripción:     Clase encargada de normalizar cadenas de texto
 *                  que contienen datos numéricos separados por
 *                  comas o por saltos de línea. Su función principal
 *                  es preparar los datos para que otras clases
 *                  puedan procesarlos de forma segura.
 ******************************************************************/

import java.util.Arrays;

public class Data
{

    /**
     * Normaliza una cadena de entrada que contiene valores numéricos.
     *
     * Este método puede recibir datos en dos formatos:
     * <ul>
     *     <li><b>Valores separados por líneas</b>, por ejemplo:<br>
     *         130<br>
     *         650<br>
     *         99<br>
     *     </li>
     *
     *     <li><b>Valores separados por comas</b>, por ejemplo:<br>
     *         "130,650,99,150"
     *     </li>
     * </ul>
     *
     * El método detecta automáticamente si el contenido está dividido
     * por líneas o por comas, realiza los cortes necesarios y devuelve
     * un arreglo de Strings limpio y sin espacios.
     *
     * @param data cadena cruda que contiene los valores X o Y.
     * @return un arreglo String[] con todos los valores normalizados.
     */
    public String[] saveData(String data)
    {
        // Validación: si viene vacía o nula → retornar arreglo vacío
        if (data == null || data.isBlank()) 
        {
            return new String[0];
        }

        // 1) Intento de división por saltos de línea
        //    (si es un listado tipo archivo)
        String[] byLine = data.split("\\R+");
        if (byLine.length > 1) 
        {
            return trimAll(byLine);
        }

        // 2) Si no viene por líneas, se intenta un formato CSV
        //    separando por coma con espacios opcionales.
        String[] byComma = data.split("\\s*,\\s*");
        return trimAll(byComma);
    }


    /**
     * Realiza limpieza general sobre cada elemento del arreglo:
     * <ul>
     *     <li>Elimina espacios en blanco antes y después</li>
     *     <li>Elimina elementos vacíos o nulos</li>
     * </ul>
     *
     * Esta función garantiza que los módulos matemáticos reciban
     * únicamente valores válidos.
     *
     * @param in arreglo de cadenas a limpiar.
     * @return un nuevo arreglo sin espacios ni elementos vacíos.
     */
    private String[] trimAll(String[] in)
    {
        return Arrays.stream(in)
                .map(s -> s == null ? "" : s.trim()) // Limpieza básica
                .filter(s -> !s.isBlank())           // Elimina elementos vacíos
                .toArray(String[]::new);             // Regresa nuevo arreglo
    }
}


