package org.breaze.network;

/**
 * Interfaz que define la configuración necesaria
 * para una conexión segura (SSL).
 *
 * Extiende la configuración TCP básica y agrega
 * los datos necesarios para establecer una conexión segura.
 */
public interface ISSLConfig extends ITCPConfig {

    /**
     * Devuelve el host o servidor al que se realizará la conexión.
     *
     * @return dirección del servidor
     */
    String getHost();

    /**
     * Devuelve la ruta del archivo truststore.
     *
     * @return ruta del truststore
     */
    String getTrustStorePath();

    /**
     * Devuelve la contraseña del truststore.
     *
     * @return contraseña del truststore
     */
    String getTrustStorePassword();
}
