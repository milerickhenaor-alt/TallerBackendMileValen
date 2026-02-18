package org.breaze;

import org.breaze.business.BioGuardMessageProcessor;
import org.breaze.business.IMessageProcessor;
import org.breaze.common.IConfigReader;
import org.breaze.common.PropertiesManager;
import org.breaze.network.INetworkService;
import org.breaze.network.ISSLConfig;
import org.breaze.network.TCPConfig;
import org.breaze.network.SSLTCPServer;

/**
 * Clase principal que inicia el servidor BioGuard.
 *
 * Se encarga de:
 * 1. Cargar la configuración desde application.properties.
 * 2. Crear la configuración SSL/TCP.
 * 3. Inicializar el procesador de mensajes.
 * 4. Levantar el servidor seguro.
 */
public class ServidorBioGuard {

    /**
     * Punto de entrada del sistema.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {

        // Cargar configuración
        IConfigReader reader = new PropertiesManager("application.properties");

        // Configurar parámetros SSL/TCP
        ISSLConfig tcpConfig = new TCPConfig(reader);

        // Crear procesador de mensajes del sistema
        IMessageProcessor processor = new BioGuardMessageProcessor();

        // Crear e iniciar servidor seguro
        INetworkService server = new SSLTCPServer(tcpConfig, processor);
        server.start();
    }
}
