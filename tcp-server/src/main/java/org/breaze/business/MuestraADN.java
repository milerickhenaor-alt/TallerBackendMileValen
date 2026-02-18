package org.breaze.business;

/**
 * Representa una muestra de ADN asociada a un paciente.
 *
 * Contiene el documento del paciente, la fecha en que se tomó
 * la muestra y la secuencia genética correspondiente.
 */
public class MuestraADN {

    // Documento del paciente al que pertenece la muestra
    private String documentoPaciente;

    // Fecha en que se tomó la muestra
    private String fecha;

    // Secuencia genética de la muestra
    private String secuencia;

    // Separador usado para convertir el objeto a línea de texto
    private static final String SEPARADOR = ";";

    /**
     * Constructor principal.
     *
     * @param documentoPaciente documento del paciente
     * @param fecha fecha de la muestra
     * @param secuencia secuencia genética
     * @throws FormatoSecuenciaInvalidoException si la secuencia es inválida
     */
    public MuestraADN(String documentoPaciente, String fecha, String secuencia)
            throws FormatoSecuenciaInvalidoException {

        // Se limpia la secuencia (sin espacios y en mayúsculas)
        secuencia = secuencia.trim().toUpperCase();

        this.documentoPaciente = documentoPaciente;
        this.fecha = fecha;
        this.secuencia = secuencia;
    }

    /**
     * Constructor que crea una muestra a partir de una línea de texto.
     * El formato esperado es: documento;fecha;secuencia
     *
     * @param lineaTexto línea con los datos separados por ";"
     * @throws FormatoSecuenciaInvalidoException si el formato es incorrecto
     */
    public MuestraADN(String lineaTexto)
            throws FormatoSecuenciaInvalidoException {

        if (lineaTexto == null || lineaTexto.isEmpty()) {
            throw new FormatoSecuenciaInvalidoException("Línea inválida.");
        }

        String[] datos = lineaTexto.split(SEPARADOR);

        if (datos.length < 3) {
            throw new FormatoSecuenciaInvalidoException("Formato incorrecto.");
        }

        String doc = datos[0];
        String fecha = datos[1];
        String sec = datos[2].trim().toUpperCase();

        this.documentoPaciente = doc;
        this.fecha = fecha;
        this.secuencia = sec;
    }

    /**
     * Convierte la muestra en una línea de texto
     * usando el separador definido.
     *
     * @return representación en formato texto
     */
    public String toTextLine() {
        return documentoPaciente + SEPARADOR +
                fecha + SEPARADOR +
                secuencia;
    }

    /**
     * Devuelve el documento del paciente.
     */
    public String getDocumentoPaciente() { return documentoPaciente; }

    /**
     * Devuelve la fecha de la muestra.
     */
    public String getFecha() { return fecha; }

    /**
     * Devuelve la secuencia genética.
     */
    public String getSecuencia() { return secuencia; }
}
