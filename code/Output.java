/******************************************************************
 * Programa:        Output.java
 * Autor:           Axel Olmedo Blanco
 * Fecha:           2025-11-25
 * Descripción:     Escribe texto en un archivo de salida utilizando
 *                  la codificación UTF-8. Si el archivo no existe,
 *                  lo crea automáticamente.
 ******************************************************************/

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Output
{

    /**
     * Escribe texto en un archivo especificado.  
     * Si el archivo no existe, será creado.  
     * Si existe, su contenido será sobrescrito.
     *
     * @param outFile nombre del archivo de salida (por ejemplo: "resultado.txt").
     * @param outText texto que se escribirá dentro del archivo.
     * @throws IOException si ocurre un error al crear o escribir el archivo.
     */
    public void writeData(String outFile, String outText) throws IOException
    {
        // Escribe la cadena usando codificación UTF-8
        Files.writeString(
            Path.of(outFile),
            outText,
            StandardCharsets.UTF_8
        );
    }
}

