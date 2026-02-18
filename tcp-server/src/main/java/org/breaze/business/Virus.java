package org.breaze.business;

/**
 * Representa un virus dentro del sistema.
 *
 * Cada virus tiene:
 * - Nombre
 * - Nivel de infecciosidad (por ejemplo: Bajo, Medio, Alto)
 * - Secuencia genética
 *
 * La información se guarda y se lee desde archivos de texto,
 * usando punto y coma (;) como separador.
 */
public class Virus {

    /** Nombre del virus */
    private String nombre;

    /** Nivel de infecciosidad del virus */
    private String nivelInfecciosidad;

    /** Secuencia genética del virus */
    private String secuencia;

    /** Separador usado para guardar el virus en archivos */
    private static final String SEPARADOR = ";";

    /**
     * Crea un virus a partir de sus datos individuales.
     *
     * @param nombre nombre del virus
     * @param nivelInfecciosidad nivel de infecciosidad
     * @param secuencia secuencia genética
     */
    public Virus(String nombre,
                 String nivelInfecciosidad,
                 String secuencia)
            throws FormatoSecuenciaInvalidoException {

        secuencia = secuencia.trim().toUpperCase();

        this.nombre = nombre;
        this.nivelInfecciosidad = nivelInfecciosidad;
        this.secuencia = secuencia;
    }

    /**
     * Crea un virus a partir de una línea de texto
     * leída desde un archivo.
     *
     * El formato esperado es:
     *
     * nombre;nivelInfecciosidad;secuencia
     *
     * @param lineaTexto línea del archivo
     */
    public Virus(String lineaTexto)
            throws FormatoSecuenciaInvalidoException {

        if (lineaTexto == null || lineaTexto.isEmpty()) {
            throw new FormatoSecuenciaInvalidoException("Línea inválida.");
        }

        String[] datos = lineaTexto.split(SEPARADOR);

        if (datos.length < 3) {
            throw new FormatoSecuenciaInvalidoException("Formato incorrecto.");
        }

        this.nombre = datos[0];
        this.nivelInfecciosidad = datos[1];
        this.secuencia = datos[2].trim().toUpperCase();
    }

    /**
     * Convierte el virus en una línea de texto
     * para poder guardarlo en un archivo.
     */
    public String toTextLine() {
        return nombre + SEPARADOR +
                nivelInfecciosidad + SEPARADOR +
                secuencia;
    }

    // --- GETTERS ---

    public String getNombre() {
        return nombre;
    }

    public String getNivelInfecciosidad() {
        return nivelInfecciosidad;
    }

    public String getSecuencia() {
        return secuencia;
    }

    // --- SETTERS ---

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNivelInfecciosidad(String nivelInfecciosidad) {
        this.nivelInfecciosidad = nivelInfecciosidad;
    }

    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }
}
