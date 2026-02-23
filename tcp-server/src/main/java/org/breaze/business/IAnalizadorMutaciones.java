package org.breaze.business;

import java.util.List;

public interface IAnalizadorMutaciones {
    /** Valida que la secuencia cumpla los estándares biológicos */
    void validarFormato(String adn) throws FormatoSecuenciaInvalidoException;

    /** Compara dos secuencias y retorna los rangos de diferencias */
    List<String> detectarMutaciones(String actual, String anterior);
}