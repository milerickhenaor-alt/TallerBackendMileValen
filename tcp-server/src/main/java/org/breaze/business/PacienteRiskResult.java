package org.breaze.business;

import java.util.List;

/**
 * Representa el resultado del análisis de riesgo de un paciente,
 * incluyendo la cantidad total de virus detectados y su clasificación.
 */
public class PacienteRiskResult {

    private int totalVirus;
    private int altamenteInfecciosos;
    private List<String> normales;
    private List<String> altos;

    /**
     * Constructor que inicializa los datos del resultado de riesgo.
     *
     * @param totalVirus Cantidad total de virus detectados.
     * @param altamenteInfecciosos Cantidad de virus altamente infecciosos.
     * @param normales Lista de virus con nivel normal.
     * @param altos Lista de virus con nivel alto.
     */
    public PacienteRiskResult(int totalVirus,
                              int altamenteInfecciosos,
                              List<String> normales,
                              List<String> altos) {
        this.totalVirus = totalVirus;
        this.altamenteInfecciosos = altamenteInfecciosos;
        this.normales = normales;
        this.altos = altos;
    }

    /**
     * Indica si el paciente es considerado de alto riesgo.
     *
     * @return true si tiene más de dos virus altamente infecciosos.
     */
    public boolean esAltoRiesgo() {
        return altamenteInfecciosos > 2;
    }

    /**
     * Obtiene la cantidad de virus altamente infecciosos.
     *
     * @return Número de virus altamente infecciosos.
     */
    public int getAltamenteInfecciosos() {
        return altamenteInfecciosos;
    }

    /**
     * Obtiene la lista de virus con nivel normal.
     *
     * @return Lista de virus normales.
     */
    public List<String> getNormales() {
        return normales;
    }

    /**
     * Obtiene la cantidad total de virus detectados.
     *
     * @return Total de virus.
     */
    public int getTotalVirus() {
        return totalVirus;
    }

    /**
     * Obtiene la lista de virus altamente infecciosos.
     *
     * @return Lista de virus con nivel alto.
     */
    public List<String> getAltos() {
        return altos;
    }
}