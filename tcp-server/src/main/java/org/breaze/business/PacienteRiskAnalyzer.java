package org.breaze.business;

import java.util.ArrayList;
import java.util.List;

public class PacienteRiskAnalyzer implements IPacienteRiskAnalyzer {

    private final VirusService virusService;

    public PacienteRiskAnalyzer(VirusService virusService) {
        this.virusService = virusService;
    }

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
