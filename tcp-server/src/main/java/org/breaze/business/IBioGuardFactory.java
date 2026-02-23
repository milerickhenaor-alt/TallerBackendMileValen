package org.breaze.business;

public interface IBioGuardFactory {
    Paciente crearPaciente(String datos);
    Virus crearVirus(String datos);
    MuestraADN crearMuestra(String datos);
}