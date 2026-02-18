package org.breaze.network;

import org.breaze.common.IConfigReader;
import org.breaze.network.ISSLConfig;

/**
 * Implementación de la configuración TCP y SSL.
 *
 * Esta clase obtiene los valores necesarios (host, puerto,
 * truststore, etc.) desde una fuente de configuración
 * usando IConfigReader.
 */
public class TCPConfig implements ISSLConfig {

    /**
     * Lector de configuración que permite obtener
     * los valores desde un archivo u otra fuente.
     */
    private final IConfigReader configReader;

    /**
     * Constructor que recibe el lector de configuración.
     *
     * @param configReader lector que proporciona los valores
     */
    public TCPConfig(IConfigReader configReader){
        this.configReader = configReader;
    }

    /**
     * Devuelve la dirección del servidor.
     */
    @Override
    public String getHost() {
        return configReader.getString("server.address");
    }

    /**
     * Devuelve el puerto del servidor.
     */
    @Override
    public int getPort() {
        return configReader.getInt("server.port");
    }

    /**
     * Devuelve la ruta del archivo truststore
     * usado para la conexión segura.
     */
    @Override
    public String getTrustStorePath() {
        return configReader.getString("ssl.truststore.path");
    }

    /**
     * Devuelve la contraseña del truststore.
     */
    @Override
    public String getTrustStorePassword() {
        return configReader.getString("ssl.truststore.password");
    }
}
