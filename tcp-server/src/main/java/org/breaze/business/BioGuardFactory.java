package org.breaze.business;

/**
 * Fábrica encargada de centralizar la creación de objetos
 * relacionados con el dominio BioGuard como Paciente, Virus y MuestraADN.
 */
public class BioGuardFactory implements IBioGuardFactory {

    /**
     * Crea una instancia de Paciente a partir de los datos proporcionados.
     *
     * @param datos Información necesaria para crear el paciente.
     * @return Nueva instancia de Paciente.
     */
    @Override
    public Paciente crearPaciente(String datos) {
        // Centralizamos la lógica de instanciación
        return new Paciente(datos);
    }

    /**
     * Crea una instancia de Virus a partir de una cadena en formato FASTA.
     * El encabezado debe contener el nombre y el nivel separados por '|'.
     *
     * @param datosFasta Datos del virus en formato FASTA.
     * @return Nueva instancia de Virus o null si el formato es inválido.
     */
    @Override
    public Virus crearVirus(String datosFasta) {
        String[] lineas = datosFasta.split("\n");
        if (lineas.length >= 2 && lineas[0].startsWith(">")) {
            String header = lineas[0].substring(1); // "Ebola|Normal"
            String[] partes = header.split("\\|");
            String nombre = partes[0];
            String nivel = partes[1];
            String secuencia = lineas[1].trim();

            return new Virus(nombre, nivel, secuencia);
        }
        return null;
    }

    /**
     * Crea una instancia de MuestraADN a partir de una cadena en formato FASTA.
     * El encabezado debe contener el documento y la fecha separados por '|'.
     *
     * @param datosFasta Datos de la muestra en formato FASTA.
     * @return Nueva instancia de MuestraADN.
     * @throws IllegalArgumentException Si el formato FASTA es inválido.
     */
    @Override
    public MuestraADN crearMuestra(String datosFasta) {
        // 1. Dividir el mensaje para separar encabezado de secuencia
        String[] lineas = datosFasta.split("\n");

        // Verificamos que tenga al menos el encabezado y la secuencia
        if (lineas.length >= 2 && lineas[0].startsWith(">")) {
            // Quitamos el '>' y separamos por '|'
            String header = lineas[0].substring(1);
            String[] partes = header.split("\\|");

            String documento = partes[0].trim();
            String fecha = partes[1].trim();

            // Unimos el resto de las líneas por si la secuencia tiene saltos de línea
            StringBuilder secuenciaBuilder = new StringBuilder();
            for (int i = 1; i < lineas.length; i++) {
                secuenciaBuilder.append(lineas[i].trim());
            }
            String secuencia = secuenciaBuilder.toString();

            // Retornamos el objeto construido correctamente
            return new MuestraADN(documento, fecha, secuencia);
        } else {
            throw new IllegalArgumentException("Formato FASTA de muestra inválido");
        }
    }
}