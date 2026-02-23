package org.breaze.business;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de persistencia para archivos en formato FASTA.
 * Permite guardar líneas, leer contenido y listar archivos
 * dentro de una ruta específica.
 */
public class PersistenciaFASTA implements IPersistenciaTexto {

    /**
     * Guarda una línea en un archivo FASTA.
     * Si la carpeta no existe, la crea automáticamente.
     *
     * @param ruta Ruta del archivo.
     * @param linea Línea a escribir.
     * @throws IOException Si ocurre un error de escritura.
     */
    @Override
    public void guardarLinea(String ruta, String linea) throws IOException {
        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            out.println(linea);
        }
    }

    /**
     * Lee las líneas de un archivo FASTA ignorando líneas vacías.
     *
     * @param ruta Ruta del archivo.
     * @return Lista de líneas leídas.
     * @throws IOException Si ocurre un error de lectura.
     */
    @Override
    public List<String> leerLineas(String ruta) throws IOException {
        List<String> lineas = new ArrayList<>();
        File file = new File(ruta);

        if (!file.exists()) return lineas;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    lineas.add(linea);
                }
            }
        }
        return lineas;
    }

    /**
     * Lista los archivos dentro de una ruta que coincidan
     * con la extensión especificada.
     *
     * @param ruta Ruta donde se buscarán los archivos.
     * @param extension Extensión a filtrar.
     * @return Lista de nombres de archivos encontrados.
     */
    @Override
    public List<String> listarArchivosEnRuta(String ruta, String extension) {
        List<String> nombres = new ArrayList<>();
        File carpeta = new File(ruta);
        if (carpeta.exists() && carpeta.isDirectory()) {
            File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(extension));
            if (archivos != null) {
                for (File f : archivos) nombres.add(f.getName());
            }
        }
        return nombres;
    }
}