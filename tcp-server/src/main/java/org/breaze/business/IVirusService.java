package org.breaze.business;

import java.util.List;

public interface IVirusService {
    Virus buscarVirus(String nombre);
    public List<Virus> cargarTodosLosVirus();
    public boolean registrarVirus(Virus virus);
}
