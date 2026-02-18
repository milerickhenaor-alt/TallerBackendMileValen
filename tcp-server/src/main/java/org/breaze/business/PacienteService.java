package org.breaze.business;

import org.breaze.common.Paciente;
import java.io.*;

/**
 * Servicio encargado de gestionar los pacientes del sistema.
 *
 * Los datos se almacenan en un archivo CSV ubicado en:
 * data/pacientes.csv
 *
 * Permite:
 * - Registrar nuevos pacientes
 * - Buscar pacientes por documento
 */
public class PacienteService implements IPacienteService {

    /** Ruta del archivo donde se guardan los pacientes */
    private static final String ARCHIVO = "data/pacientes.csv";

    /**
     * Registra un nuevo paciente en el archivo.
     *
     * Si el paciente ya existe (mismo documento),
     * se lanza una excepción para evitar duplicados.
     *
     * @param p paciente a registrar
     * @return true si se guardó correctamente, false si ocurrió un error
     * @throws PacienteDuplicadoException si el paciente ya está registrado
     */
    @Override
    public boolean registrarPaciente(Paciente p) throws PacienteDuplicadoException {

        // Verificar si ya existe
        if (buscarPaciente(p.getDocumento()) != null) {
            throw new PacienteDuplicadoException("Paciente ya existe");
        }

        try {
            // Crear carpeta si no existe
            File archivo = new File(ARCHIVO);
            File carpeta = archivo.getParentFile();

            if (carpeta != null && !carpeta.exists()) {
                carpeta.mkdirs();
            }

            // Guardar paciente al final del archivo
            try (FileWriter fw = new FileWriter(archivo, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                out.println(p.toTextLine());
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca un paciente por su documento.
     *
     * Recorre el archivo línea por línea hasta encontrar
     * una coincidencia.
     *
     * @param documento número de documento del paciente
     * @return el paciente encontrado o null si no existe
     */
    @Override
    public Paciente buscarPaciente(String documento) {

        File file = new File(ARCHIVO);

        // Si el archivo aún no existe
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {

            String linea;

            while ((linea = br.readLine()) != null) {

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
