package org.breaze.business;

import org.breaze.excepciones.PacienteDuplicadoException;

/**
 * Interfaz que define las operaciones relacionadas
 * con los pacientes del sistema.
 *
 * Las clases que implementen esta interfaz deben
 * encargarse del registro y consulta de pacientes.
 */
public interface IPacienteService {

    /**
     * Registra un nuevo paciente.
     *
     * @param p paciente que se desea registrar
     * @return true si el registro fue exitoso
     * @throws PacienteDuplicadoException si el paciente ya existe
     */
    boolean registrarPaciente(Paciente p) throws PacienteDuplicadoException;

    /**
     * Busca un paciente por su documento.
     *
     * @param documento número de documento del paciente
     * @return paciente encontrado o null si no existe
     */
    Paciente buscarPaciente(String documento);
}
