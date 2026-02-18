package org.breaze.network;

import org.breaze.network.IMessageService;
import org.breaze.network.ITCPConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Cliente TCP básico.
 *
 * Esta clase se encarga de conectarse a un servidor usando
 * protocolo TCP, enviar un mensaje y devolver la respuesta.
 */
public class TCPClient implements IMessageService {

    /**
     * Configuración necesaria para la conexión TCP
     * (host y puerto).
     */
    private final ITCPConfig tcpConfig;

    /**
     * Constructor que recibe la configuración de conexión.
     *
     * @param tcpConfig datos del servidor al que se conectará
     */
    public TCPClient(ITCPConfig tcpConfig){
        this.tcpConfig = tcpConfig;
    }

    /**
     * Se conecta al servidor, envía el mensaje y
     * devuelve la respuesta recibida.
     *
     * @param message mensaje que se desea enviar
     * @return respuesta del servidor o un mensaje de error
     */
    @Override
    public String sendMessage(String message) {

        // Se crea el socket con el host y puerto configurados
        try(Socket socket = new Socket(tcpConfig.getHost(), tcpConfig.getPort())){

            // Flujo para enviar datos al servidor
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Flujo para recibir datos del servidor
            DataInputStream in = new DataInputStream(socket.getInputStream());

            // Mensaje informativo de conexión
            System.out.println("[TCP] Conectados a servidor %s en puerto %s"
                    .formatted(tcpConfig.getHost(), tcpConfig.getPort()));

            // Se envía el mensaje
            out.writeUTF(message);
            out.flush();

            System.out.println("[TCP] Mensaje enviado: %s"
                    .formatted(message));

            // Se espera y lee la respuesta del servidor
            String response = in.readUTF();

            System.out.println("[TCP] Respuesta: %s"
                    .formatted(response));

            return response;

        } catch (UnknownHostException _) {

            // Error cuando el host no existe o no se puede resolver
            System.out.println("[TCP] Error de host: no se encuentra el host: %s"
                    .formatted(tcpConfig.getHost()));

            return "ERROR_HOST_DESCONOCIDO";

        } catch (IOException e) {

            // Error general de comunicación
            System.out.println("[TCP] Error critico %s"
                    .formatted(e.getMessage()));

            return "ERROR_COMUNICACION";
        }

    }
}
