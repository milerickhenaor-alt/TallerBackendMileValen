package org.breaze.business;

import java.util.List;

/**
 * Define el contrato para la gestión de virus dentro del sistema.
 * Permite registrar, buscar y cargar virus almacenados.
 */
public interface IVirusService {

    /**
     * Busca un virus por su nombre.
     *
     * @param nombre Nombre del virus.
     * @return Instancia del virus si existe, o null si no se encuentra.
     */
    Virus buscarVirus(String nombre);

    /**
     * Carga todos los virus disponibles en el sistema.
     *
     * @return Lista de virus registrados.
     */
    public List<Virus> cargarTodosLosVirus();

    /**
     * Registra un nuevo virus en el sistema.
     *
     * @param virus Instancia del virus a registrar.
     * @return true si el registro fue exitoso, false en caso contrario.
     */
    public boolean registrarVirus(Virus virus);
}