package org.breaze.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de la lógica de comparación de secuencias.
 * Trabaja únicamente con cadenas de texto (String),
 * lo que facilita la lectura y análisis de archivos de ADN.
 */
public class AnalizadorGenetico implements IAnalizadorGenetico{

    /**
     * Realiza el diagnóstico comparando la secuencia de ADN
     * del paciente con un catálogo de virus.
     *
     * @param secuenciaADN Secuencia de ADN del paciente
     * @param catalogoVirus Lista de virus registrados en el sistema
     * @return Lista de coincidencias encontradas en el ADN
     */

    @Override
    public List<ResultadoDiagnostico> realizarDiagnostico(String secuenciaADN, List<Virus> catalogoVirus) {

        // Lista donde se guardarán los resultados encontrados
        List<ResultadoDiagnostico> resultados = new ArrayList<>();

        // Se recorre cada virus del catálogo
        for (Virus v : catalogoVirus) {

            // Se obtiene la secuencia genética del virus
            String secuenciaVirus = v.getSecuencia();

            // Se busca la secuencia del virus dentro del ADN del paciente
            int indice = secuenciaADN.indexOf(secuenciaVirus);

            // Mientras se encuentren coincidencias
            while (indice != -1) {

                // Se crea un resultado indicando:
                // - Nombre del virus
                // - Posición inicial donde se encontró
                // - Posición final donde termina la coincidencia
                resultados.add(new ResultadoDiagnostico(
                        v.getNombre(),
                        indice,
                        indice + secuenciaVirus.length()
                ));

                // Se sigue buscando desde la siguiente posición
                // para detectar múltiples apariciones del mismo virus
                indice = secuenciaADN.indexOf(secuenciaVirus, indice + 1);
            }
        }

        // Se devuelve la lista de coincidencias encontradas
        return resultados;
    }
}
