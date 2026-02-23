package org.breaze.business;

import java.util.List;

public class PacienteRiskResult {

    private int totalVirus;
    private int altamenteInfecciosos;
    private List<String> normales;
    private List<String> altos;

    public PacienteRiskResult(int totalVirus,
                              int altamenteInfecciosos,
                              List<String> normales,
                              List<String> altos) {
        this.totalVirus = totalVirus;
        this.altamenteInfecciosos = altamenteInfecciosos;
        this.normales = normales;
        this.altos = altos;
    }

    public boolean esAltoRiesgo() {
        return altamenteInfecciosos > 2;
    }

    public int getAltamenteInfecciosos() {
        return altamenteInfecciosos;
    }

    public List<String> getNormales() {
        return normales;
    }

    public int getTotalVirus() {
        return totalVirus;
    }

    public List<String> getAltos() {
        return altos;
    }
}
