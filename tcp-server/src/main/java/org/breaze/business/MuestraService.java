package org.breaze.business;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MuestraService implements IMuestraService {

    private static final String CARPETA_BASE = "data/muestras/";

    @Override
    public boolean registrarMuestras(MuestraADN muestra) throws FormatoSecuenciaInvalidoException {
        if(muestra.getSecuencia() != null && !muestra.getSecuencia().isEmpty() && !muestra.getSecuencia().matches("[ATGC]+")){
            throw new FormatoSecuenciaInvalidoException(" \"La secuencia solo puede contener A, T, G y C.\"");
        }

        try {
            File carpetaPaciente = new File(CARPETA_BASE + muestra.getDocumentoPaciente());
            if (!carpetaPaciente.exists()) {
                carpetaPaciente.mkdirs();
            }

            File archivoMuestra = new File(
                    carpetaPaciente,
                    muestra.getFecha() + ".fasta"
            );

            try (PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(archivoMuestra)))) {

                out.println(">" + muestra.getDocumentoPaciente() + "|" + muestra.getFecha());
                out.println(muestra.getSecuencia());
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void generarDiagnosticoCSV(
            MuestraADN muestra,
            List<ResultadoDiagnostico> resultados) {

        try {
            File carpetaPaciente = new File(CARPETA_BASE + muestra.getDocumentoPaciente());

            File archivoCSV = new File(
                    carpetaPaciente,
                    "diagnostico_" + muestra.getFecha() + ".csv"
            );

            try (PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(archivoCSV)))) {

                out.println("virus,posicion_inicio,posicion_fin");

                for (ResultadoDiagnostico r : resultados) {
                    out.println(
                            r.getNombreVirus() + "," +
                                    r.getPosicionInicio() + "," +
                                    r.getPosicionFin()
                    );
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> detectarMutaciones(String actual, String anterior) {

        List<String> rangos = new ArrayList<>();

        int minLength = Math.min(actual.length(), anterior.length());

        int inicio = -1;

        for (int i = 0; i < minLength; i++) {

            if (actual.charAt(i) != anterior.charAt(i)) {

                if (inicio == -1) {
                    inicio = i;
                }

            } else {

                if (inicio != -1) {
                    rangos.add(inicio + "-" + (i - 1));
                    inicio = -1;
                }
            }
        }

        if (inicio != -1) {
            rangos.add(inicio + "-" + (minLength - 1));
        }

        return rangos;
    }

    private String leerSecuencia(File archivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            br.readLine(); // Saltar header FASTA
            return br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String generarReporteMutaciones(String documento) {

        String rutaPaciente = "data/muestras/" + documento;

        File carpeta = new File(rutaPaciente);

        if (!carpeta.exists()) {
            return "[ERROR] No existen muestras para este paciente.";
        }

        File[] archivos = carpeta.listFiles(
                (dir, name) -> name.endsWith(".fasta")
        );

        if (archivos == null || archivos.length < 2) {
            return "[ERROR] Se necesitan al menos 2 muestras para comparar.";
        }

        Arrays.sort(archivos); // Ordena por nombre (fecha)

        File muestraActualFile = archivos[archivos.length - 1];

        String actual = leerSecuencia(muestraActualFile);

        StringBuilder resultado = new StringBuilder();

        resultado.append("Comparando muestra actual con historial:\n");

        for (int i = 0; i < archivos.length - 1; i++) {

            String anterior = leerSecuencia(archivos[i]);

            List<String> mutaciones = detectarMutaciones(actual, anterior);

            resultado.append("Contra ")
                    .append(archivos[i].getName())
                    .append(" -> ");

            if (mutaciones.isEmpty()) {
                resultado.append("Sin cambios\n");
            } else {
                resultado.append("Mutaciones en posiciones: ")
                        .append(mutaciones)
                        .append("\n");
            }
        }

        return resultado.toString();
    }

}
