package org.breaze.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación encargada de analizar el nivel de riesgo
 * de un paciente según los virus detectados.
 */
public class PacienteRiskAnalyzer implements IPacienteRiskAnalyzer {

    private final VirusService virusService;

    /**
     * Constructor que recibe el servicio de virus
     * necesario para consultar el nivel de infecciosidad.
     *
     * @param virusService Servicio de gestión de virus.
     */
    public PacienteRiskAnalyzer(VirusService virusService) {
        this.virusService = virusService;
    }

    /**
     * Analiza la lista de virus detectados y clasifica el riesgo
     * según el nivel de infecciosidad de cada uno.
     *
     * @param virusDetectados Lista de nombres de virus encontrados.
     * @return Resultado del análisis de riesgo del paciente.
     */
    @Override
    public PacienteRiskResult analizar(List<String> virusDetectados) {

        int total = virusDetectados.size();
        int altamente = 0;

        List<String> normales = new ArrayList<>();
        List<String> altos = new ArrayList<>();

        for (String nombreVirus : virusDetectados) {

            Virus virus = virusService.buscarVirus(nombreVirus);

            if (virus == null) continue;

            if ("Alto".equalsIgnoreCase(virus.getNivelInfecciosidad())) {
                altamente++;
                altos.add(nombreVirus);
            } else {
                normales.add(nombreVirus);
            }
        }

        return new PacienteRiskResult(total, altamente, normales, altos);
    }

}