package org.breaze.business;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementación de persistencia basada en archivos CSV.
 * Permite listar archivos, guardar líneas y leer contenido
 * desde el sistema de archivos.
 */
public class PersistenciaCSV implements IPersistenciaTexto {

    /**
     * Lista los archivos en una ruta específica que coincidan
     * con la extensión indicada.
     *
     * @param ruta Ruta donde se buscarán los archivos.
     * @param extension Extensión a filtrar (ejemplo: ".csv").
     * @return Lista de nombres de archivos encontrados.
     */
    @Override
    public List<String> listarArchivosEnRuta(String ruta, String extension) {
        File carpeta = new File(ruta);
        if (!carpeta.exists() || !carpeta.isDirectory()) return new ArrayList<>();

        String[] lista = carpeta.list((dir, name) -> name.endsWith(extension));
        return (lista == null) ? new ArrayList<>() : Arrays.asList(lista);
    }

    /**
     * Guarda una línea de texto en el archivo especificado.
     * Si el archivo o carpeta no existen, los crea.
     *
     * @param ruta Ruta del archivo.
     * @param linea Contenido a escribir.
     * @throws IOException Si ocurre un error de escritura.
     */
    @Override
    public void guardarLinea(String ruta, String linea) throws IOException {

        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        try (FileWriter fw = new FileWriter(archivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(linea);
        }
    }

    /**
     * Lee todas las líneas de un archivo especificado.
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
                lineas.add(linea);
            }
        }

        return lineas;
    }

}