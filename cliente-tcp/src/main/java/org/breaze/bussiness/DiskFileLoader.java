package bussiness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implementación de carga de archivos desde el disco local.
 * Permite leer el contenido completo de un archivo de texto
 * y devolverlo como una cadena.
 */
public class DiskFileLoader implements IFileLoader {

    /**
     * Carga el contenido de un archivo ubicado en una ruta específica.
     *
     * @param path Ruta del archivo a leer.
     * @return Contenido del archivo como String.
     *         Retorna cadena vacía si el archivo no existe
     *         o ocurre un error de lectura.
     */
    @Override
    public String load(String path) {
        StringBuilder sb = new StringBuilder();
        File file = new File(path);

        if (!file.exists()) return "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudo leer el archivo local: " + e.getMessage());
            return "";
        }

        return sb.toString().trim();
    }
}