package bussiness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LocalFileHandler {

    /**
     * Lee un archivo FASTA local y devuelve su contenido.
     * Esta es la única responsabilidad de esta clase.
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