/******************************************************************
 * Programa:        Input.java
 * Autor:           Axel Olmedo Blaco
 * Fecha:           2025-11-25
 * Descripción:     Lee un archivo de texto  y
 *                  retorna todo su contenido como un String.
 ******************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Input
{
  

    private String data;

    /**
     * Reader utilizado opcionalmente para lectura buffered.
     * (No se usa directamente en esta implementación pero se deja
     *  por consistencia con el diagrama UML.)
     */
    private BufferedReader br = null;

    /**
     * Lee un archivo de texto usando codificación UTF-8 y devuelve su contenido
     * completo en un único String.
     *
     * @param inFile nombre del archivo a leer (por ejemplo "test.txt").
     * @return el contenido completo del archivo.
     * @throws IOException si el archivo no existe o no puede leerse.
     */
    public String readData(String inFile) throws IOException
    {
        // Lee el archivo completo en una sola operación
        String content = Files.readString(
            Path.of(inFile),
            StandardCharsets.UTF_8
        );

        // Guarda internamente el contenido leído
        this.data = content;

        return content;
    }
}


