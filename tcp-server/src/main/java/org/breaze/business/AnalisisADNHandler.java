package org.breaze.business;

import java.util.List;

public class AnalisisADNHandler implements IActionHandler{
    private final IVirusService virusService;
    private final IAnalizadorGenetico analizador;
    private final IMuestraService muestraService;

    public AnalisisADNHandler(IVirusService virusService, IAnalizadorGenetico analizador, IMuestraService muestraService) {
        this.virusService = virusService;
        this.analizador = analizador;
        this.muestraService = muestraService;
    }

    @Override
    public String execute(String datos) {
        MuestraADN muestra = new MuestraADN(datos);
        muestraService.registrarMuestras(muestra);

        List<Virus> catalogo = virusService.cargarTodosLosVirus();
        List<ResultadoDiagnostico> resultados = analizador.realizarDiagnostico(muestra.getSecuencia(), catalogo);

        muestraService.generarDiagnosticoCSV(muestra, resultados);

        // La lógica de "formateo de informe" vive aquí o en un ReportService
        return "esto hay que organizarlo";
    }
}
