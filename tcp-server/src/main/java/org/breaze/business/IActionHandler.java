package org.breaze.business;

/**
 * Define el contrato para los manejadores de acciones del sistema.
 * Cada implementación debe ejecutar una operación específica
 * a partir de los datos recibidos.
 */
public interface IActionHandler {

    /**
     * Ejecuta la acción correspondiente utilizando los datos proporcionados.
     *
     * @param datos Información necesaria para ejecutar la acción.
     * @return Resultado de la ejecución en formato texto.
     */
    public String execute(String datos);
}