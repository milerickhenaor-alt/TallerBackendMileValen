package org.breaze.business;

import org.breaze.excepciones.PacienteDuplicadoException;

import java.io.IOException;
import java.util.List;

public class PacienteService implements IPacienteService {

    private static final String ARCHIVO = "data/pacientes.csv";

    private final IPersistenciaTexto persistencia;

    public PacienteService(IPersistenciaTexto persistencia) {
        this.persistencia = persistencia;
    }

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