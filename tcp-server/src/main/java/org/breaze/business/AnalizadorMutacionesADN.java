package org.breaze.business;

import java.util.ArrayList;
import java.util.List;

public class AnalizadorMutacionesADN implements IAnalizadorMutaciones {

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

    private String formatearRango(int inicio, int fin) {
        // Si el inicio y el fin son iguales, devolvemos solo un número (ej: "3")
        // Si son diferentes, devolvemos el rango (ej: "4-6")
        return (inicio == fin) ? String.valueOf(inicio) : inicio + "-" + fin;
    }
}