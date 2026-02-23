package org.breaze.business;

import java.util.List;

public class ReporteMutacionesBasico {

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