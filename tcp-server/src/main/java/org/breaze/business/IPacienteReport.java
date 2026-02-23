package org.breaze.business;

/**
 * Define el contrato para la generación de reportes
 * relacionados con los pacientes del sistema.
 */
public interface IPacienteReport {

    /**
     * Genera el reporte correspondiente de pacientes.
     *
     * @return Reporte generado en formato texto.
     */
    public String generarReporte();
}