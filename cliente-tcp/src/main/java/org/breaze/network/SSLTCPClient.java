package org.breaze.network;

import org.breaze.network.IMessageService;
import org.breaze.network.ISSLConfig;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;

/**
 * Cliente TCP con conexión segura (SSL).
 *
 * Esta clase se encarga de conectarse a un servidor
 * usando SSL, enviar un mensaje y devolver la respuesta.
 */

public class SSLTCPClient implements IMessageService {
    /**
     * Configuración necesaria para establecer la conexión SSL.
     */
    private final ISSLConfig sslConfig;

    /**
     * Constructor que recibe la configuración de conexión segura.
     *
     * @param sslConfig datos necesarios para conectarse al servidor
     */
    public SSLTCPClient(ISSLConfig sslConfig){
        this.sslConfig = sslConfig;
    }

    /**
     * Crea la fábrica de sockets SSL usando el truststore configurado.
     *
     * @return SSLSocketFactory lista para crear conexiones seguras
     * @throws Exception si ocurre algún problema al cargar el truststore
     */
    private SSLSocketFactory createSSLSocketFactory() throws Exception {

        KeyStore ts = KeyStore.getInstance("PKCS12");
        char[] pwd = sslConfig.getTrustStorePassword().toCharArray();

        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream(sslConfig.getTrustStorePath())) {

            if (is == null) {
                throw new FileNotFoundException("Truststore no encontrado");
            }

            ts.load(is, pwd);

            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());

            tmf.init(ts);

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tmf.getTrustManagers(), null);

            return ctx.getSocketFactory();
        }
    }

    /**
     * Se conecta al servidor, envía un mensaje y devuelve la respuesta.
     *
     * @param message mensaje que se desea enviar
     * @return respuesta del servidor o un mensaje de error
     */
    @Override
    public String sendMessage(String message) {

        try (Socket socket = createSSLSocketFactory()
                .createSocket(sslConfig.getHost(), sslConfig.getPort())) {

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            System.out.println("[TCP] Conectados a servidor %s en puerto %s"
                    .formatted(sslConfig.getHost(), sslConfig.getPort()));

            out.writeUTF(message);
            out.flush();

            System.out.println("[TCP] Mensaje enviado: %s"
                    .formatted(message));

            String response = in.readUTF();

            System.out.println("[TCP] Respuesta: %s"
                    .formatted(response));

            return response;

        } catch (UnknownHostException _) {

            System.out.println("[TCP] Error: no se encuentra el host %s"
                    .formatted(sslConfig.getHost()));

            return "ERROR_HOST_DESCONOCIDO";

        } catch (IOException e) {

            System.out.println("[TCP] Error de comunicación: %s"
                    .formatted(e.getMessage()));

            return "ERROR_COMUNICACION";

        } catch (Exception e) {

            System.err.println("[TCP] Error crítico del sistema: "
                    + e.getMessage());

            return "ERROR_CRITICO";
        }
    }
}
