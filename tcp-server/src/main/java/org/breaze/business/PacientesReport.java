package org.breaze.business;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de generar un reporte de pacientes
 * con alto riesgo, según la cantidad de virus altamente
 * infecciosos detectados en sus diagnósticos.
 *
 * Lee los archivos de diagnóstico guardados en:
 * data/muestras/
 *
 * Y genera un archivo:
 * data/reporte_alto_riesgo.csv
 */
public class PacientesReport {

    /** Carpeta donde se almacenan las muestras de los pacientes */
    private static final String CARPETA_MUESTRAS = "data/muestras/";

    /** Archivo donde se genera el reporte final */
    private static final String ARCHIVO_REPORTE = "data/reporte_alto_riesgo.csv";

    /** Servicio para consultar información de los virus */
    private final VirusService virusService = new VirusService();

    /**
     * Genera el reporte de pacientes con alto riesgo.
     *
     * Un paciente se considera de alto riesgo si tiene
     * más de 3 virus con nivel de infecciosidad "Alto".
     *
     * El reporte incluye:
     * - Documento del paciente
     * - Total de virus detectados
     * - Cantidad de virus altamente infecciosos
     * - Lista de virus normales
     * - Lista de virus altamente infecciosos
     */
    public void generarReporte() {

        File carpetaBase = new File(CARPETA_MUESTRAS);

        if (!carpetaBase.exists()) {
            return;
        }

        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(ARCHIVO_REPORTE)))) {

            // Encabezado del CSV
            out.println("Documento,total_virus,altamente_infecciosos,virus_normal_bajo,virus_altamente_infecciosos");

            File[] pacientes = carpetaBase.listFiles(File::isDirectory);

            if (pacientes == null) return;

            for (File carpetaPaciente : pacientes) {

                String documento = carpetaPaciente.getName();
                List<String> virusDetectados = new ArrayList<>();

                // Buscar archivos de diagnóstico del paciente
                File[] archivos = carpetaPaciente.listFiles(
                        (dir, name) -> name.startsWith("diagnostico_") && name.endsWith(".csv")
                );

                if (archivos == null) continue;

                for (File archivo : archivos) {

                    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

                        br.readLine(); // Saltar encabezado

                        String linea;
                        while ((linea = br.readLine()) != null) {

                            String[] partes = linea.split(",");
                            virusDetectados.add(partes[0]); // nombre del virus
                        }

                    }
                }

                procesarPaciente(documento, virusDetectados, out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Analiza los virus detectados de un paciente
     * y escribe la información en el reporte si cumple
     * la condición de alto riesgo.
     *
     * @param documento identificador del paciente
     * @param virusDetectados lista de virus encontrados
     * @param out escritor del archivo de reporte
     */
    private void procesarPaciente(String documento,
                                  List<String> virusDetectados,
                                  PrintWriter out) {

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

        // Se considera alto riesgo si tiene más de 3 virus altamente infecciosos
        if (altamente > 3) {

            out.println(
                    documento + "," +
                            total + "," +
                            altamente + "," +
                            normales + "," +
                            altos
            );
        }
    }
}
