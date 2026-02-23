package org.breaze.business;

/**
 * Define el contrato para la fábrica encargada
 * de crear las entidades principales del sistema BioGuard.
 */
public interface IBioGuardFactory {

    /**
     * Crea una instancia de Paciente a partir de los datos proporcionados.
     *
     * @param datos Información necesaria para crear el paciente.
     * @return Nueva instancia de Paciente.
     */
    Paciente crearPaciente(String datos);

    /**
     * Crea una instancia de Virus a partir de los datos proporcionados.
     *
     * @param datos Información necesaria para crear el virus.
     * @return Nueva instancia de Virus.
     */
    Virus crearVirus(String datos);

    /**
     * Crea una instancia de MuestraADN a partir de los datos proporcionados.
     *
     * @param datos Información necesaria para crear la muestra.
     * @return Nueva instancia de MuestraADN.
     */
    MuestraADN crearMuestra(String datos);
}