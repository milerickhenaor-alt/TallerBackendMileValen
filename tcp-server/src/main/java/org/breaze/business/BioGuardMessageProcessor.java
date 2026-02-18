package org.breaze.business;

import org.breaze.common.Paciente;
import java.util.List;

/**
 * Clase que se encarga de recibir y procesar los mensajes
 * enviados por el cliente.
 *
 * El mensaje debe tener el formato:
 *
 *     ACCION:DATOS
 *
 * Según la acción recibida, se llama al servicio correspondiente
 * para ejecutar la operación.
 */
public class BioGuardMessageProcessor implements IMessageProcessor {

    // Servicios principales del sistema
    private final PacienteService pacienteService = new PacienteService();
    private final VirusService virusService = new VirusService();
    private final MuestraService muestraService = new MuestraService();
    private final AnalizadorGenetico analizador = new AnalizadorGenetico();

    /**
     * Procesa el mensaje recibido desde el cliente.
     *
     * @param request mensaje con el formato ACCION:DATOS
     * @return respuesta generada por el sistema
     */
    @Override
    public String process(String request) {

        try {

            // Si el mensaje está vacío
            if (request == null || request.isEmpty()) {
                return "[ERROR] Mensaje vacío";
            }

            // Separar acción y datos
            String[] partes = request.split(":", 2);

            if (partes.length < 2) {
                return "[ERROR] Formato inválido. Use ACCION:DATOS";
            }

            String accion = partes[0];
            String datos = partes[1];

            // Ejecutar según la acción recibida
            switch (accion) {

                case "REGISTRAR_PACIENTE":
                    return procesarRegistro(datos);

                case "CONSULTAR_PACIENTE":
                    return procesarConsulta(datos);

                case "REGISTRAR_VIRUS":
                    return procesarRegistroVirus(datos);

                case "ANALIZAR_ADN":
                    return procesarAnalisis(datos);

                case "REPORTE_PACIENTES":
                    return procesarReporte();

                case "REPORTE_MUTACIONES":
                    return procesarMutaciones(datos);

                default:
                    return "[ERROR] Acción no reconocida";
            }

        } catch (Exception e) {
            return "[ERROR] " + e.getMessage();
        }
    }

    /**
     * Consulta un paciente por su documento.
     */
    private String procesarConsulta(String documento) {

        Paciente p = pacienteService.buscarPaciente(documento);

        if (p == null) {
            return "[ERROR] Paciente no encontrado";
        }

        return "[OK] " + p.toTextLine();
    }

    /**
     * Registra un nuevo paciente.
     */
    private String procesarRegistro(String datos)
            throws PacienteDuplicadoException {

        Paciente paciente = new Paciente(datos);
        pacienteService.registrarPaciente(paciente);

        return "Paciente registrado exitosamente.";
    }

    /**
     * Registra un virus en el sistema.
     */
    private String procesarRegistroVirus(String datos)
            throws FormatoSecuenciaInvalidoException {

        Virus virus = new Virus(datos);
        boolean ok = virusService.registrarVirus(virus);

        return ok ? "[OK] Virus registrado"
                : "[ERROR] Formato inválido";
    }

    /**
     * Analiza una muestra de ADN y genera un informe.
     */
    private String procesarAnalisis(String datos) {

        MuestraADN muestra = new MuestraADN(datos);

        // Guardar muestra
        muestraService.registrarMuestras(muestra);

        // Cargar virus
        List<Virus> catalogo = virusService.cargarTodosLosVirus();

        // Analizar ADN
        List<ResultadoDiagnostico> resultados =
                analizador.realizarDiagnostico(
                        muestra.getSecuencia(),
                        catalogo
                );

        // Generar archivo CSV
        muestraService.generarDiagnosticoCSV(muestra, resultados);

        // Crear informe en texto
        StringBuilder informe = new StringBuilder();
        informe.append("--- INFORME MÉDICO ---\n");
        informe.append("Virus encontrados: ")
                .append(resultados.size())
                .append("\n");

        for (ResultadoDiagnostico r : resultados) {
            informe.append(r.toString()).append("\n");
        }

        return informe.toString();
    }

    /**
     * Genera el reporte general de pacientes.
     */
    private String procesarReporte() {

        PacientesReport pacientesReport = new PacientesReport();
        pacientesReport.generarReporte();

        return "El reporte se generó exitosamente.";
    }

    /**
     * Genera el reporte de mutaciones de un paciente.
     */
    private String procesarMutaciones(String documento) {
        return muestraService.generarReporteMutaciones(documento);
    }
}
