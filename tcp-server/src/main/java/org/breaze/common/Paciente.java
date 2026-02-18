package org.breaze.common;

/**
 * Representa un paciente del sistema.
 *
 * Esta clase permite:
 * - Crear pacientes manualmente.
 * - Crear pacientes a partir de una línea de texto (leída desde archivo).
 * - Convertir el paciente a formato texto para guardarlo en archivo.
 */
public class Paciente {

    private String documento;
    private String nombre;
    private String apellido;
    private int edad;
    private String correo;
    private String genero;
    private String ciudad;
    private String pais;

    /** Separador estándar usado en el archivo de texto */
    private static final String SEPARADOR = ";";

    /**
     * Constructor completo.
     *
     * @param documento número de documento
     * @param nombre nombre del paciente
     * @param apellido apellido del paciente
     * @param edad edad del paciente
     * @param correo correo electrónico
     * @param genero género
     * @param ciudad ciudad de residencia
     * @param pais país de residencia
     */
    public Paciente(String documento, String nombre, String apellido, int edad,
                    String correo, String genero, String ciudad, String pais) {

        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.genero = genero;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    /**
     * Constructor que crea un Paciente a partir de una línea de texto.
     *
     * Se utiliza cuando se lee una línea desde un archivo.
     * El formato esperado es:
     * documento;nombre;apellido;edad;correo;genero;ciudad;pais
     *
     * @param lineaTexto línea leída desde archivo
     */
    public Paciente(String lineaTexto) {

        if (lineaTexto != null && !lineaTexto.isEmpty()) {

            String[] datos = lineaTexto.split(SEPARADOR);

            if (datos.length >= 8) {
                this.documento = datos[0];
                this.nombre = datos[1];
                this.apellido = datos[2];
                this.edad = Integer.parseInt(datos[3]);
                this.correo = datos[4];
                this.genero = datos[5];
                this.ciudad = datos[6];
                this.pais = datos[7];
            }
        }
    }

    /**
     * Convierte el objeto en una línea de texto lista para guardar en archivo.
     *
     * @return cadena en formato plano separada por ";"
     */
    public String toTextLine() {

        return documento + SEPARADOR +
                nombre + SEPARADOR +
                apellido + SEPARADOR +
                edad + SEPARADOR +
                correo + SEPARADOR +
                genero + SEPARADOR +
                ciudad + SEPARADOR +
                pais;
    }

    // Getters
    public String getDocumento() { return documento; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getEdad() { return edad; }
    public String getCorreo() { return correo; }
    public String getGenero() { return genero; }
    public String getCiudad() { return ciudad; }
    public String getPais() { return pais; }

    // Setters
    public void setDocumento(String documento) { this.documento = documento; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setPais(String pais) { this.pais = pais; }
}
