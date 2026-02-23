package org.breaze.business;

import java.util.List;

/**
 * Define el contrato para el componente encargado
 * de analizar el nivel de riesgo de un paciente
 * a partir de los virus detectados.
 */
public interface IPacienteRiskAnalyzer {

    /**
     * Analiza el riesgo del paciente según la lista de virus detectados.
     *
     * @param virusDetectados Lista de nombres de virus encontrados.
     * @return Resultado del análisis de riesgo del paciente.
     */
    PacienteRiskResult analizar(List<String> virusDetectados);
}