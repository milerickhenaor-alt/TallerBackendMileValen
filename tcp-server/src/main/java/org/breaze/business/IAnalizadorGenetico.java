package org.breaze.business;

import java.util.List;

public interface IAnalizadorGenetico {
    // Definimos el contrato, usamos la interfaz List, basicamente copiamos lo que tienes en la clase
    List<ResultadoDiagnostico> realizarDiagnostico(String secuenciaADN, List<Virus> catalogoVirus);

}