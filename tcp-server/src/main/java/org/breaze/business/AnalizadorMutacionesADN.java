package org.breaze.business;

import org.breaze.excepciones.FormatoSecuenciaInvalidoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación encargada de validar secuencias de ADN
 * y detectar mutaciones entre una secuencia actual y una anterior.
 */
public class AnalizadorMutacionesADN implements IAnalizadorMutaciones {

    /**
     * Valida que la secuencia de ADN no sea nula, esté vacía
     * y que solo contenga caracteres válidos (A, T, G, C).
     *
     * @param adn Secuencia de ADN a validar.
     * @throws FormatoSecuenciaInvalidoException Si la secuencia es inválida.
     */
    @Override
    public void validarFormato(String adn) throws FormatoSecuenciaInvalidoException {
        if (adn == null || adn.isEmpty()) {
            throw new FormatoSecuenciaInvalidoException("La secuencia no puede estar vacía.");
        }

        String adnMayuscula = adn.toUpperCase();
        for (int i = 0; i < adnMayuscula.length(); i++) {
            char letra = adnMayuscula.charAt(i);
            if (letra != 'A' && letra != 'T' && letra != 'G' && letra != 'C') {
                throw new FormatoSecuenciaInvalidoException(
                        "Carácter inválido '" + letra + "' detectado en posición " + (i + 1)
                );
            }
        }
    }

    /**
     * Detecta las diferencias entre dos secuencias de ADN
     * y devuelve los rangos de posiciones donde existen mutaciones.
     *
     * @param actual Secuencia actual de ADN.
     * @param anterior Secuencia anterior de ADN.
     * @return Lista de rangos en formato texto donde se detectaron mutaciones.
     */
    @Override
    public List<String> detectarMutaciones(String actual, String anterior) {
        List<String> rangos = new ArrayList<>();
        // 1. Consideramos la longitud máxima para no ignorar el sobrante
        int maxLength = Math.max(actual.length(), anterior.length());
        int minLength = Math.min(actual.length(), anterior.length());

        int inicio = -1;

        for (int i = 0; i < maxLength; i++) {
            boolean hayDiferencia;

            if (i < minLength) {
                // Comparación base a base
                hayDiferencia = (actual.charAt(i) != anterior.charAt(i));
            } else {
                // Si llegamos aquí, una secuencia terminó y la otra sigue (Mutación por longitud)
                hayDiferencia = true;
            }

            if (hayDiferencia) {
                if (inicio == -1) inicio = i + 1; // Usamos i + 1 para que sea legible al humano (base 1)
            } else {
                if (inicio != -1) {
                    rangos.add(formatearRango(inicio, i));
                    inicio = -1;
                }
            }
        }

        // Cerrar el último rango si quedó abierto
        if (inicio != -1) {
            rangos.add(formatearRango(inicio, maxLength));
        }

        return rangos;
    }

    /**
     * Formatea un rango de posiciones de mutación.
     * Si inicio y fin son iguales, devuelve una sola posición.
     * Si son diferentes, devuelve un rango en formato inicio-fin.
     *
     * @param inicio Posición inicial del rango.
     * @param fin Posición final del rango.
     * @return Rango formateado como texto.
     */
    private String formatearRango(int inicio, int fin) {
        return (inicio == fin) ? String.valueOf(inicio) : inicio + "-" + fin;
    }
}