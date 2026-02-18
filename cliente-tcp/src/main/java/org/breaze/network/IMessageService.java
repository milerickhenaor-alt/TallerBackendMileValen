package org.breaze.network;

/**
 * Interfaz que define el envío de mensajes.
 *
 * Las clases que implementen esta interfaz
 * deben encargarse de cómo se envía el mensaje
 * (por ejemplo: por red, API, socket, etc.).
 */
public interface IMessageService {

    /**
     * Envía un mensaje y devuelve una respuesta.
     *
     * @param message mensaje que se desea enviar
     * @return respuesta obtenida después del envío
     */
    String sendMessage(String message);
}
