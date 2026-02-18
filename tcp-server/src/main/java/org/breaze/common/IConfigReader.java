package org.breaze.common;

/**
 * Interfaz para leer valores de configuración.
 *
 * Permite obtener propiedades desde un archivo
 * de configuración (por ejemplo .properties).
 */
public interface IConfigReader {

    /**
     * Obtiene un valor de tipo String.
     *
     * @param key clave de la propiedad
     * @return valor asociado o null si no existe
     */
    String getString(String key);

    /**
     * Obtiene un valor de tipo int.
     *
     * @param key clave de la propiedad
     * @return valor numérico asociado
     */
    int getInt(String key);

    /**
     * Obtiene un valor de tipo boolean.
     *
     * @param key clave de la propiedad
     * @return true o false según la configuración
     */
    boolean getBoolean(String key);
}
