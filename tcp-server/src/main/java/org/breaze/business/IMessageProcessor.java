package org.breaze.business;

/**
 * Interfaz encargada de definir cómo se procesan
 * los mensajes recibidos por el sistema.
 *
 * Las clases que implementen esta interfaz deben
 * interpretar el mensaje y devolver una respuesta.
 */
public interface IMessageProcessor {

    /**
     * Procesa un mensaje recibido y genera una respuesta.
     *
     * @param message mensaje enviado por el cliente
     * @return respuesta generada por el sistema
     */
    String process(String message);
}
