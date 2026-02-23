package bussiness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase encargada de manejar la lectura de archivos locales.
 * Su responsabilidad es leer archivos en formato FASTA
 * almacenados en el disco.
 */
public class LocalFileHandler {

    /**
     * Lee un archivo FASTA local y devuelve su contenido como texto.
     *
     * @param ruta Ruta del archivo FASTA.
     * @return Contenido del archivo como String.
     *         Retorna cadena vacía si el archivo no existe
     *         o ocurre un error de lectura.
     */
    public String leerArchivoFasta(String ruta) {
        StringBuilder contenido = new StringBuilder();
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return "";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            System.err.println("[ERROR LOCAL] Fallo al leer disco: " + e.getMessage());
            return "";
        }

        return contenido.toString().trim();
    }
}