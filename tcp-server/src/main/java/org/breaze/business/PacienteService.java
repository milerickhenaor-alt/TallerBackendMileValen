package org.breaze.business;

import org.breaze.excepciones.PacienteDuplicadoException;

import java.io.IOException;
import java.util.List;

/**
 * Implementación del servicio encargado de gestionar
 * las operaciones relacionadas con los pacientes.
 */
public class PacienteService implements IPacienteService {

    private static final String ARCHIVO = "data/pacientes.csv";

    private final IPersistenciaTexto persistencia;

    /**
     * Constructor que recibe el mecanismo de persistencia
     * para almacenar y consultar pacientes.
     *
     * @param persistencia Implementación de persistencia en texto.
     */
    public PacienteService(IPersistenciaTexto persistencia) {
        this.persistencia = persistencia;
    }

    /**
     * Registra un nuevo paciente en el sistema.
     *
     * @param p Paciente a registrar.
     * @return true si el registro fue exitoso, false en caso de error de escritura.
     * @throws PacienteDuplicadoException Si el paciente ya existe.
     */
    @Override
    public boolean registrarPaciente(Paciente p) throws PacienteDuplicadoException {

        Paciente existente = buscarPaciente(p.getDocumento());

        if (existente != null) {
            throw new PacienteDuplicadoException("Paciente ya existe");
        }

        try {
            persistencia.guardarLinea(ARCHIVO, p.toTextLine());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Busca un paciente por su documento.
     *
     * @param documento Documento del paciente.
     * @return Instancia del paciente si existe, o null si no se encuentra.
     */
    @Override
    public Paciente buscarPaciente(String documento) {

        try {
            List<String> lineas = persistencia.leerLineas(ARCHIVO);

            for (String linea : lineas) {
                Paciente p = new Paciente(linea);

                if (p.getDocumento().equals(documento)) {
                    return p;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}