package org.breaze.common;

/**
 * Interfaz que permite leer valores de configuración
 * usando una clave.
 *
 * Puede usarse para obtener datos desde un archivo,
 * variables de entorno u otra fuente de configuración.
 */
public interface IConfigReader {

    /**
     * Devuelve el valor asociado a la clave en formato texto.
     *
     * @param key clave del valor que se quiere obtener
     * @return valor como texto
     */
    String getString(String key);

    /**
     * Devuelve el valor asociado a la clave en formato número entero.
     *
     * @param key clave del valor que se quiere obtener
     * @return valor como número entero
     */
    int getInt(String key);

    /**
     * Devuelve el valor asociado a la clave en formato verdadero o falso.
     *
     * @param key clave del valor que se quiere obtener
     * @return valor como booleano (true o false)
     */
    boolean getBoolean(String key);
}
