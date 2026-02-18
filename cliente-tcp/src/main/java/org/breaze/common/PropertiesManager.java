package org.breaze.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase encargada de leer valores desde un archivo de propiedades (.properties).
 *
 * Permite obtener los valores como texto, número entero o verdadero/falso.
 */
public class PropertiesManager implements IConfigReader {

    /**
     * Objeto que almacena las propiedades cargadas del archivo.
     */
    Properties props = new Properties();

    /**
     * Constructor que carga el archivo de propiedades.
     *
     * @param fileName nombre del archivo que se encuentra en los recursos del proyecto
     */
    public PropertiesManager(String fileName){
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {

            if (is == null) {
                throw new RuntimeException("Error: No se encontró el archivo");
            }

            props.load(is);

        } catch (IOException e) {
            System.out.println("Error crítico al leer las propiedades: " + e.getMessage());
        }
    }

    /**
     * Devuelve el valor asociado a la clave como texto.
     */
    @Override
    public String getString(String key) {
        return props.getProperty(key);
    }

    /**
     * Devuelve el valor asociado a la clave como número entero.
     */
    @Override
    public int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }

    /**
     * Devuelve el valor asociado a la clave como verdadero o falso.
     */
    @Override
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(props.getProperty(key));
    }
}
