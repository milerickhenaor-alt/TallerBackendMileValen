package org.breaze.business;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar los virus del sistema.
 *
 * Los virus se guardan en archivos individuales dentro de:
 * data/virus/
 *
 * Cada virus se almacena en formato FASTA.
 */
public class VirusService {

    /** Carpeta donde se guardan los archivos de virus */
    private static final String CARPETA = "data/virus/";

    /**
     * Registra un nuevo virus en el sistema.
     *
     * - Verifica que no exista otro virus con el mismo nombre.
     * - Valida que la secuencia solo contenga A, T, G y C.
     * - Guarda el virus en formato FASTA.
     *
     * @param virus virus a registrar
     * @return true si se guardó correctamente, false si ocurrió un error
     * @throws VirusDuplicadoException si el virus ya existe
     * @throws FormatoSecuenciaInvalidoException si la secuencia es inválida
     */
    public boolean registrarVirus(Virus virus)
            throws VirusDuplicadoException, FormatoSecuenciaInvalidoException {

        // Verificar duplicado
        if (buscarVirus(virus.getNombre()) != null) {
            throw new VirusDuplicadoException("Virus ya existe");
        }

        // Validar secuencia
        if (virus.getSecuencia() != null &&
                !virus.getSecuencia().isEmpty() &&
                !virus.getSecuencia().matches("[ATGC]+")) {

            throw new FormatoSecuenciaInvalidoException(
                    "La secuencia solo puede contener A, T, G y C."
            );
        }

        try {
            File archivo = new File(CARPETA + virus.getNombre() + ".fasta");
            File carpeta = archivo.getParentFile();

            // Crear carpeta si no existe
            if (carpeta != null && !carpeta.exists()) {
                carpeta.mkdirs();
            }

            // Guardar en formato FASTA
            try (FileWriter fw = new FileWriter(archivo);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {

                out.println(">" + virus.getNombre() + "|" + virus.getNivelInfecciosidad());
                out.println(virus.getSecuencia());

                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca un virus por su nombre.
     *
     * @param nombre nombre del virus
     * @return el virus encontrado o null si no existe
     */
    public Virus buscarVirus(String nombre) {

        File archivo = new File(CARPETA + nombre + ".fasta");

        if (!archivo.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String header = br.readLine();
            String secuencia = br.readLine();

            if (header != null && header.startsWith(">")) {

                String datos = header.substring(1); // quitar >
                String[] partes = datos.split("\\|");

                String nombreVirus = partes[0];
                String nivel = partes[1];

                return new Virus(nombreVirus, nivel, secuencia);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Carga todos los virus registrados en el sistema.
     *
     * Recorre la carpeta de virus y reconstruye cada uno
     * leyendo su archivo FASTA.
     *
     * @return lista con todos los virus encontrados
     */
    public List<Virus> cargarTodosLosVirus() {

        List<Virus> lista = new ArrayList<>();

        File carpeta = new File(CARPETA);

        if (!carpeta.exists()) {
            return lista;
        }

        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".fasta"));

        if (archivos == null) {
            return lista;
        }

        for (File archivo : archivos) {

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

                String header = br.readLine();
                String secuencia = br.readLine();

                if (header != null && header.startsWith(">")) {

                    String datos = header.substring(1);
                    String[] partes = datos.split("\\|");

                    if (partes.length >= 2) {

                        String nombre = partes[0];
                        String nivel = partes[1];

                        lista.add(new Virus(nombre, nivel, secuencia));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }
}
