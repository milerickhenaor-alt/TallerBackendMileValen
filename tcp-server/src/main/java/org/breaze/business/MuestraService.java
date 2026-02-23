package org.breaze.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Servicio encargado de la gestión de muestras de ADN.
 * Cumple con SRP al delegar la lógica de análisis y reporte a componentes especializados.
 */
public class MuestraService implements IMuestraService {

    private final IPersistenciaTexto persistencia;
    private final IAnalizadorMutaciones analizadorMutaciones;
    private final ReporteMutacionesBasico reporteGenerator;
    private static final String CARPETA_BASE = "data/muestras/";

    public MuestraService(IPersistenciaTexto persistencia, IAnalizadorMutaciones analizador) {
        this.persistencia = persistencia;
        this.analizadorMutaciones = analizador;
        this.reporteGenerator = new ReporteMutacionesBasico();
    }

    @Override
    public boolean registrarMuestras(MuestraADN muestra) throws FormatoSecuenciaInvalidoException {
        // 1. Validar ADN (Responsabilidad delegada al analizador manual)
        analizadorMutaciones.validarFormato(muestra.getSecuencia());

        // 2. Definir ruta: CARPETA_BASE/ID_PACIENTE/FECHA.fasta
        // Esto cumple con: "organizar las muestras en carpetas individuales por paciente"
        String directorioPaciente = CARPETA_BASE + muestra.getDocumentoPaciente() + "/";
        String nombreArchivo = muestra.getFecha() + ".fasta";
        String rutaCompleta = directorioPaciente + nombreArchivo;

        try {
            // 3. Formatear según estándar FASTA:
            // Línea 1: >documento|fecha
            // Línea 2: secuencia
            String contenidoFasta = ">" + muestra.getDocumentoPaciente() + "|" + muestra.getFecha() + "\n"
                    + muestra.getSecuencia();

            // 4. Persistir (DIP)
            persistencia.guardarLinea(rutaCompleta, contenidoFasta);
            return true;
        } catch (IOException e) {
            // Log de error o manejo de excepción de infraestructura
            return false;
        }
    }

    @Override
    public void generarDiagnosticoCSV(MuestraADN muestra, List<ResultadoDiagnostico> resultados) {
        String ruta = CARPETA_BASE + muestra.getDocumentoPaciente() + "/diagnostico_" + muestra.getFecha() + ".csv";

        try {
            // Escribir cabecera
            persistencia.guardarLinea(ruta, "virus,posicion_inicio,posicion_fin");

            // Escribir cada resultado
            for (ResultadoDiagnostico r : resultados) {
                String linea = r.getNombreVirus() + "," + r.getPosicionInicio() + "," + r.getPosicionFin();
                persistencia.guardarLinea(ruta, linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generarReporteMutaciones(String documento) {
        String rutaCarpeta = CARPETA_BASE + documento + "/";

        // Obtenemos la lista de archivos FASTA del paciente
        List<String> archivos = persistencia.listarArchivosEnRuta(rutaCarpeta, ".fasta");

        if (archivos.isEmpty()) {
            return "[ERROR] No existen muestras registradas para este paciente.";
        }
        if (archivos.size() < 2) {
            return "[ERROR] Se requiere al menos dos muestras para generar un comparativo de mutaciones.";
        }

        // Ordenamos para asegurar que comparamos cronológicamente (por fecha en el nombre)
        Collections.sort(archivos);

        // La última muestra es la "actual"
        String rutaActual = rutaCarpeta + archivos.get(archivos.size() - 1);
        String adnActual = extraerSecuencia(rutaActual);

        List<String> nombresHistorial = new ArrayList<>();
        List<List<String>> mutacionesPorArchivo = new ArrayList<>();

        // Comparamos la actual contra todas las anteriores
        for (int i = 0; i < archivos.size() - 1; i++) {
            String rutaAnterior = rutaCarpeta + archivos.get(i);
            String adnAnterior = extraerSecuencia(rutaAnterior);

            nombresHistorial.add(archivos.get(i));
            // Delegamos el algoritmo de comparación al analizador
            mutacionesPorArchivo.add(analizadorMutaciones.detectarMutaciones(adnActual, adnAnterior));
        }

        // Delegamos la construcción del String final al experto en reportes
        return reporteGenerator.construirInforme(documento, nombresHistorial, mutacionesPorArchivo);
    }

    /**
     * Método privado para extraer solo la secuencia de un archivo FASTA.
     */
    private String extraerSecuencia(String ruta) {
        try {
            List<String> lineas = persistencia.leerLineas(ruta);
            // En un FASTA estándar: línea 0 es el header (>...), línea 1 es la secuencia
            return (lineas.size() >= 2) ? lineas.get(1) : "";
        } catch (IOException e) {
            return "";
        }
    }
}