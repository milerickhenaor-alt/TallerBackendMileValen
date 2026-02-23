package org.breaze.business;

import java.util.List;

/**
 * Manejador encargado de ejecutar el proceso de análisis de ADN.
 * Se encarga de registrar la muestra, obtener el catálogo de virus,
 * realizar el diagnóstico y generar el reporte correspondiente.
 */
public class AnalisisADNHandler implements IActionHandler{
    private final IVirusService virusService;
    private final IAnalizadorGenetico analizador;
    private final IMuestraService muestraService;

    /**
     * Constructor que inicializa los servicios necesarios
     * para ejecutar el análisis de ADN.
     *
     * @param virusService Servicio encargado de gestionar los virus.
     * @param analizador Servicio que realiza el diagnóstico genético.
     * @param muestraService Servicio encargado de gestionar las muestras.
     */
    public AnalisisADNHandler(IVirusService virusService, IAnalizadorGenetico analizador, IMuestraService muestraService) {
        this.virusService = virusService;
        this.analizador = analizador;
        this.muestraService = muestraService;
    }

    /**
     * Ejecuta el flujo completo del análisis:
     * <ul>
     *     <li>Crea una nueva muestra de ADN.</li>
     *     <li>Registra la muestra.</li>
     *     <li>Carga el catálogo de virus.</li>
     *     <li>Realiza el diagnóstico.</li>
     *     <li>Genera el reporte en formato CSV.</li>
     * </ul>
     *
     * @param datos Secuencia de ADN a analizar.
     * @return Resultado del proceso en formato texto.
     */
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