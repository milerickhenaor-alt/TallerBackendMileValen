package org.breaze.business;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Clase que maneja la conexión de un cliente.
 *
 * Cada vez que un cliente se conecta al servidor,
 * se crea un nuevo hilo (Thread) para atenderlo
 * de manera independiente.
 */
public class ClientHandler extends Thread {

    // Socket que representa la conexión con el cliente
    private final Socket socket;

    // Procesador encargado de interpretar el mensaje recibido
    private final IMessageProcessor processor;

    /**
     * Constructor que recibe el socket del cliente
     * y el procesador de mensajes.
     *
     * @param socket conexión activa con el cliente
     * @param processor lógica que procesa el mensaje recibido
     */
    public ClientHandler(Socket socket,
                         IMessageProcessor processor) {
        this.socket = socket;
        this.processor = processor;
    }

    /**
     * Método que se ejecuta cuando el hilo inicia.
     *
     * Se encarga de:
     * 1. Leer el mensaje enviado por el cliente
     * 2. Procesarlo
     * 3. Enviar la respuesta
     */
    @Override
    public void run() {
        try (
                // Flujo para leer datos del cliente
                DataInputStream in =
                        new DataInputStream(socket.getInputStream());

                // Flujo para enviar datos al cliente
                DataOutputStream out =
                        new DataOutputStream(socket.getOutputStream())
        ) {

            // Se lee el mensaje enviado por el cliente
            String request = in.readUTF();

            // Se procesa el mensaje usando la lógica del sistema
            String response = processor.process(request);

            // Se envía la respuesta al cliente
            out.writeUTF(response);

        } catch (Exception e) {
            // En caso de error, se imprime en consola
            e.printStackTrace();
        }
    }
}
