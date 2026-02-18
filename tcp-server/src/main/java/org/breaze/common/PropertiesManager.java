package org.breaze.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Implementación de IConfigReader que lee configuraciones
 * desde un archivo .properties.
 *
 * El archivo debe estar en el classpath del proyecto.
 */
public class PropertiesManager implements IConfigReader {

    /** Objeto que almacena las propiedades cargadas */
    private final Properties props = new Properties();

    /**
     * Constructor que carga el archivo de configuración.
     *
     * @param fileName nombre del archivo .properties
     */
    public PropertiesManager(String fileName) {

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (is == null) {
                throw new RuntimeException("Error: no se encontró el archivo");
            }

            props.load(is);
            System.out.println("Archivo %s cargado correctamente"
                    .formatted(fileName));

        } catch (IOException e) {
            System.out.println("[CONFIG] Error al leer archivo de configuración: "
                    + e.getMessage());
        }
    }

    /**
     * Obtiene una propiedad como String.
     *
     * @param key clave de la propiedad
     * @return valor asociado o null si no existe
     */
    @Override
    public String getString(String key) {
        return props.getProperty(key);
    }

    /**
     * Obtiene una propiedad como entero.
     *
     * @param key clave de la propiedad
     * @return valor convertido a int
     * @throws NumberFormatException si el valor no es numérico
     */
    @Override
    public int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }

    /**
     * Obtiene una propiedad como boolean.
     *
     * @param key clave de la propiedad
     * @return true o false según el valor configurado
     */
    @Override
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(props.getProperty(key));
    }
}
