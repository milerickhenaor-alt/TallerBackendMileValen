package org.breaze.business;

/**
 * Excepción que se lanza cuando se intenta registrar
 * un paciente que ya existe en el sistema.
 *
 * Se utiliza para evitar duplicados al momento de
 * guardar un nuevo paciente.
 */
public class PacienteDuplicadoException extends Exception {

    /**
     * Crea la excepción con un mensaje descriptivo.
     *
     * @param mensaje detalle del error ocurrido
     */
    public PacienteDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
