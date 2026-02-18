package org.breaze.business;

/**
 * Representa el resultado de un análisis de ADN.
 *
 * Guarda la información de:
 * - Nombre del virus detectado
 * - Posición donde comienza en la secuencia
 * - Posición donde termina
 *
 * Se utiliza para generar informes y archivos CSV
 * con los resultados del diagnóstico.
 */
public class ResultadoDiagnostico {

    /** Nombre del virus detectado */
    private String nombreVirus;

    /** Posición inicial donde se encontró el virus */
    private int posicionInicio;

    /** Posición final donde se encontró el virus */
    private int posicionFin;

    /**
     * Crea un nuevo resultado de diagnóstico.
     *
     * @param nombreVirus nombre del virus encontrado
     * @param posicionInicio posición inicial en la secuencia
     * @param posicionFin posición final en la secuencia
     */
    public ResultadoDiagnostico(String nombreVirus,
                                int posicionInicio,
                                int posicionFin) {
        this.nombreVirus = nombreVirus;
        this.posicionInicio = posicionInicio;
        this.posicionFin = posicionFin;
    }

    /**
     * Devuelve el nombre del virus detectado.
     */
    public String getNombreVirus() {
        return nombreVirus;
    }

    /**
     * Devuelve la posición inicial.
     */
    public int getPosicionInicio() {
        return posicionInicio;
    }

    /**
     * Devuelve la posición final.
     */
    public int getPosicionFin() {
        return posicionFin;
    }

    /**
     * Devuelve una representación en texto del resultado,
     * por ejemplo:
     *
     * COVID19 [120 - 340]
     */
    @Override
    public String toString() {
        return nombreVirus + " [" + posicionInicio + " - " + posicionFin + "]";
    }
}
