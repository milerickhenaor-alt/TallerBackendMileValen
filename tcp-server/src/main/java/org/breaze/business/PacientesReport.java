package org.breaze.business;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada únicamente de generar el reporte
 * de pacientes de alto riesgo.
 *
 * No contiene lógica de negocio.
 * No instancia dependencias concretas.
 */
public class PacientesReport implements IPacienteReport {

    private static final String CARPETA_MUESTRAS = "data/muestras/";
    private static final String ARCHIVO_REPORTE = "data/reporte_alto_riesgo.csv";

    // Dependemos de la abstracción, no de la implementación
    private final IPacienteRiskAnalyzer riskAnalyzer;

    /**
     * Inyección de dependencias por constructor.
     * La clase no decide qué implementación usar.
     */
    public PacientesReport(IPacienteRiskAnalyzer riskAnalyzer) {
        this.riskAnalyzer = riskAnalyzer;
    }

    @Override
    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- PACIENTES DE ALTO RIESGO (UMBRAL > 3 VIRUS ALTOS) ---\n");
        sb.append(String.format("%-15s | %-10s | %-10s\n", "DOCUMENTO", "TOTAL", "ALTOS"));
        sb.append("----------------------------------------------------------\n");

        boolean huboPacientes = false;
        File carpetaBase = new File(CARPETA_MUESTRAS);

        // Validación de existencia de datos
        if (!carpetaBase.exists() || !carpetaBase.isDirectory()) {
            return "[ERROR] No existe la carpeta de muestras en el servidor.";
        }

        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(ARCHIVO_REPORTE)))) {

            // 1. Escribir encabezado en el archivo físico
            out.println("Documento,total_virus,altamente_infecciosos,virus_normal_bajo,virus_altamente_infecciosos");

            File[] pacientes = carpetaBase.listFiles(File::isDirectory);
            if (pacientes == null) {
                return "[OK] No hay carpetas de pacientes para procesar.";
            }

            for (File carpetaPaciente : pacientes) {
                String documento = carpetaPaciente.getName();
                List<String> virusDetectados = obtenerVirusDetectados(carpetaPaciente);

                // 2. Delegamos la lógica al analizador de riesgo
                PacienteRiskResult resultado = riskAnalyzer.analizar(virusDetectados);

                if (resultado.esAltoRiesgo()) {
                    huboPacientes = true;

                    // 3. Alimentar el StringBuilder para la respuesta en tiempo real (Cliente)
                    sb.append(String.format("%-15s | %-10s | %-10s\n",
                            documento,
                            resultado.getTotalVirus(),
                            resultado.getAltamenteInfecciosos()));

                    // 4. Escribir en el archivo CSV (Persistencia)
                    // Usamos String.join con ";" para que las listas no rompan las columnas del CSV
                    out.println(
                            documento + "," +
                                    resultado.getTotalVirus() + "," +
                                    resultado.getAltamenteInfecciosos() + "," +
                                    String.join(";", resultado.getNormales()) + "," +
                                    String.join(";", resultado.getAltos())
                    );
                }
            }

        } catch (IOException e) {
            return "[ERROR] Fallo al escribir el archivo de reporte: " + e.getMessage();
        }

        // Retorno condicional basado en si se encontraron hallazgos
        return huboPacientes ? sb.toString() : "[OK] No se detectaron pacientes de alto riesgo.";
    }

    /**
     * Método privado auxiliar.
     * Solo se encarga de leer archivos y extraer nombres de virus.
     * No contiene lógica de negocio.
     */
    private List<String> obtenerVirusDetectados(File carpetaPaciente) {

        List<String> virusDetectados = new ArrayList<>();

        File[] archivos = carpetaPaciente.listFiles(
                (dir, name) -> name.startsWith("diagnostico_") && name.endsWith(".csv")
        );

        if (archivos == null) return virusDetectados;

        for (File archivo : archivos) {

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

                br.readLine(); // Saltar encabezado

                String linea;
                while ((linea = br.readLine()) != null) {

                    String[] partes = linea.split(",");
                    virusDetectados.add(partes[0]);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return virusDetectados;
    }
}