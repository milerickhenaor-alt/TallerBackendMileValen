package org.breaze.business;

import java.io.IOException;
import java.util.List;

/**
 * Define el contrato para la gestión de persistencia
 * basada en archivos de texto dentro del sistema.
 */
public interface IPersistenciaTexto {

    /**
     * Lista los archivos en una ruta específica
     * que coincidan con una determinada extensión.
     *
     * @param ruta Ruta donde se buscarán los archivos.
     * @param extension Extensión de los archivos a filtrar.
     * @return Lista de nombres de archivos encontrados.
     */
    List<String> listarArchivosEnRuta(String ruta, String extension);

    /**
     * Guarda una línea de texto en la ruta especificada.
     *
     * @param ruta Ruta del archivo.
     * @param linea Contenido a guardar.
     * @throws IOException Si ocurre un error de escritura.
     */
    void guardarLinea(String ruta, String linea) throws IOException;

    /**
     * Lee todas las líneas de un archivo ubicado en la ruta especificada.
     *
     * @param ruta Ruta del archivo.
     * @return Lista de líneas leídas.
     * @throws IOException Si ocurre un error de lectura.
     */
    List<String> leerLineas(String ruta) throws IOException;
}