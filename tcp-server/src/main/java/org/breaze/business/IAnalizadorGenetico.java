package org.breaze.business;

import java.util.List;

/**
 * Define el contrato para los componentes encargados
 * de realizar análisis genéticos sobre una secuencia de ADN.
 */
public interface IAnalizadorGenetico {

    /**
     * Realiza el diagnóstico de una secuencia de ADN
     * comparándola contra un catálogo de virus.
     *
     * @param secuenciaADN Secuencia de ADN a analizar.
     * @param catalogoVirus Lista de virus contra los cuales se comparará la secuencia.
     * @return Lista de resultados del diagnóstico encontrados.
     */
    List<ResultadoDiagnostico> realizarDiagnostico(String secuenciaADN, List<Virus> catalogoVirus);

}