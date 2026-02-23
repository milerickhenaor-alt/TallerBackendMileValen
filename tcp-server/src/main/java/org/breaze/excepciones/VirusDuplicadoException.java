package org.breaze.excepciones;

/**
 * Excepción que se lanza cuando se intenta registrar
 * un virus que ya existe en el sistema.
 *
 * Se usa para evitar duplicados en el catálogo de virus.
 */
public class VirusDuplicadoException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje descriptivo.
     *
     * @param message detalle del error ocurrido
     */
    public VirusDuplicadoException(String message) {
        super(message);
    }
}
