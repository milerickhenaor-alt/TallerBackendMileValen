package org.breaze.network;

/**
 * Interfaz que define la configuración básica
 * para una conexión TCP.
 *
 * Contiene los datos necesarios para conectarse
 * a un servidor mediante host y puerto.
 */
public interface ITCPConfig {

    /**
     * Devuelve el host o dirección del servidor.
     *
     * @return dirección del servidor
     */
    String getHost();

    /**
     * Devuelve el puerto de conexión.
     *
     * @return número de puerto
     */
    int getPort();
}
