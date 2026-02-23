package org.breaze.business;

import java.util.List;

/**
 * Clase encargada de construir un reporte básico de mutaciones
 * comparando secuencias actuales con historiales previos.
 */
public class ReporteMutacionesBasico {

    /**
     * Construye un informe de mutaciones para un paciente.
     *
     * @param documento Documento del paciente.
     * @param nombresArchivos Lista de archivos históricos comparados.
     * @param listaMutaciones Lista de listas que contiene las posiciones
     *                        donde se detectaron mutaciones por cada archivo.
     * @return Texto formateado del reporte de mutaciones.
     */
    public String construirInforme(String documento, List<String> nombresArchivos, List<List<String>> listaMutaciones) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- REPORTE GENÉTICO DE MUTACIONES ---\n");
        sb.append("Paciente: ").append(documento).append("\n");
        sb.append("--------------------------------------\n");

        for (int i = 0; i < nombresArchivos.size(); i++) {
            sb.append("Comparación con historial [").append(nombresArchivos.get(i)).append("]:\n");

            List<String> mutaciones = listaMutaciones.get(i);
            if (mutaciones.isEmpty()) {
                sb.append("  > Resultado: Sin cambios detectados.\n");
            } else {
                sb.append("  > Resultado: Mutaciones detectadas en posiciones: ")
                        .append(mutaciones.toString()).append("\n");
            }
            sb.append("\n");
        }

        sb.append("Fin del reporte.");
        return sb.toString();
    }
}