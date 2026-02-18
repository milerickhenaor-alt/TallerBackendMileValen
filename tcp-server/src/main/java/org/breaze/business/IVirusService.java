package org.breaze.business;

import java.util.List;

/**
 * Interfaz que define las operaciones relacionadas
 * con los virus registrados en el sistema.
 *
 * Las clases que implementen esta interfaz deben
 * encargarse del registro y consulta de virus.
 */
public interface IVirusService {

    /**
     * Registra un virus a partir de un archivo
     * en formato FASTA.
     *
     * @param rutaFasta ruta del archivo que contiene la información del virus
     */
    void registrarVirus(String rutaFasta);

    /**
     * Devuelve la lista de virus registrados.
     *
     * @return lista de virus disponibles en el sistema
     */
    List<Virus> obtenerVirus();
}
