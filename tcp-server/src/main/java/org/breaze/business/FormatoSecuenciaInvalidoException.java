package org.breaze.business;

/**
 * Excepción personalizada que se lanza cuando
 * una secuencia genética tiene un formato inválido.
 *
 * Se usa para indicar que la secuencia no cumple
 * con las reglas esperadas (por ejemplo, que solo
 * contenga caracteres válidos como A, T, C o G).
 */
public class FormatoSecuenciaInvalidoException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de error.
     *
     * @param message descripción del problema encontrado
     */
    public FormatoSecuenciaInvalidoException(String message) {
        super(message);
    }
}
