package org.breaze.business;

/**
 * Interfaz que define las operaciones relacionadas
 * con las muestras de ADN.
 *
 * Las clases que implementen esta interfaz deben
 * encargarse de registrar y gestionar las muestras.
 */
public interface IMuestraService {

    /**
     * Registra una nueva muestra de ADN en el sistema.
     *
     * @param m muestra de ADN que se desea registrar
     * @return true si el registro fue exitoso, false en caso contrario
     */
    boolean registrarMuestras(MuestraADN m);

}
