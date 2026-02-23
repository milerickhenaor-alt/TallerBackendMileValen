package org.breaze.business;

import org.breaze.excepciones.FormatoSecuenciaInvalidoException;
import org.breaze.excepciones.PacienteDuplicadoException;

import java.util.List;

/**
 * Procesador principal de mensajes del sistema BioGuard.
 * Se encarga de interpretar la acción solicitada y delegar
 * la ejecución a los servicios correspondientes.
 */
public class BioGuardMessageProcessor implements IMessageProcessor {

    private final IPacienteService pacienteService;
    private final IVirusService virusService;
    private final IMuestraService muestraService;
    private final IAnalizadorGenetico analizador;

    private final IBioGuardFactory factory; // Única interfaz de creación
    private final IPacienteReport pacienteReport;

    /**
     * Constructor que recibe todas las dependencias necesarias
     * para el procesamiento de mensajes.
     *
     * @param pacienteService Servicio de gestión de pacientes.
     * @param virusService Servicio de gestión de virus.
     * @param muestraService Servicio de gestión de muestras.
     * @param analizador Servicio encargado del análisis genético.
     * @param factory Fábrica para la creación de entidades del dominio.
     * @param pacienteReport Generador de reportes de pacientes.
     */
    public BioGuardMessageProcessor(
            IPacienteService pacienteService,
            IVirusService virusService,
            IMuestraService muestraService,
            IAnalizadorGenetico analizador,
            IBioGuardFactory factory,
            IPacienteReport pacienteReport) {
        this.pacienteService = pacienteService;
        this.virusService = virusService;
        this.muestraService = muestraService;
        this.analizador = analizador;
        this.factory = factory;
        this.pacienteReport = pacienteReport;
    }

    /**
     * Procesa un mensaje en formato ACCION:DATOS
     * y ejecuta la operación correspondiente.
     *
     * @param request Mensaje recibido.
     * @return Resultado del procesamiento.
     */
    @Override
    public String process(String request) {
        if (request == null || request.isEmpty()) return "[ERROR] Mensaje vacío";

        String[] partes = request.split(":", 2);
        if (partes.length < 2) return "[ERROR] Formato inválido. Use ACCION:DATOS";

        String accion = partes[0];
        String datos = partes[1];

        try {
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
                    return procesarReportePacientesRiesgo();
                case "REPORTE_MUTACIONES":
                    return procesarMutaciones(datos);
                default:
                    return "[ERROR] Acción no reconocida";
            }
        } catch (PacienteDuplicadoException | FormatoSecuenciaInvalidoException e) {
            return "[ERROR] Negocio: " + e.getMessage();
        } catch (Exception e) {
            return "[ERROR] Sistema: " + e.getMessage();
        }
    }

    /**
     * Procesa el registro de un nuevo paciente.
     *
     * @param datos Datos del paciente.
     * @return Resultado de la operación.
     * @throws PacienteDuplicadoException Si el paciente ya existe.
     */
    private String procesarRegistro(String datos) throws PacienteDuplicadoException {
        Paciente p = factory.crearPaciente(datos);
        pacienteService.registrarPaciente(p);
        return "[OK] Paciente registrado exitosamente.";
    }

    /**
     * Procesa el registro de un virus en formato FASTA.
     *
     * @param contenidoFasta Contenido del virus.
     * @return Resultado de la operación.
     */
    private String procesarRegistroVirus(String contenidoFasta) {
        try {
            Virus nuevoVirus = factory.crearVirus(contenidoFasta);

            if (nuevoVirus == null) return "[ERROR] Formato FASTA inválido";

            virusService.registrarVirus(nuevoVirus);

            return "[OK] Virus '" + nuevoVirus.getNombre() + "' cargado y almacenado en el servidor.";
        } catch (Exception e) {
            return "[ERROR] " + e.getMessage();
        }
    }

    /**
     * Procesa la consulta de un paciente por documento.
     *
     * @param documento Documento del paciente.
     * @return Información del paciente o mensaje de error.
     */
    private String procesarConsulta(String documento) {
        Paciente p = pacienteService.buscarPaciente(documento);
        if (p == null) return "[ERROR] Paciente no encontrado";

        return "[OK] " + p.toTextLine();
    }

    /**
     * Procesa el análisis de una muestra de ADN en formato FASTA.
     *
     * @param contenidoFasta Contenido de la muestra.
     * @return Resultado del análisis.
     */
    private String procesarAnalisis(String contenidoFasta) {
        try {
            MuestraADN muestra = factory.crearMuestra(contenidoFasta);

            muestraService.registrarMuestras(muestra);

            List<Virus> catalogo = virusService.cargarTodosLosVirus();

            List<ResultadoDiagnostico> resultados = analizador.realizarDiagnostico(
                    muestra.getSecuencia(),
                    catalogo
            );

            muestraService.generarDiagnosticoCSV(muestra, resultados);

            return "[OK] Análisis completado. Diagnóstico generado en el servidor.";
        } catch (Exception e) {
            return "[ERROR] " + e.getMessage();
        }
    }

    /**
     * Genera el reporte de pacientes en riesgo.
     *
     * @return Reporte generado o mensaje de error.
     */
    private String procesarReportePacientesRiesgo() {
        try {
            return pacienteReport.generarReporte();
        } catch (Exception e) {
            return "[ERROR] Sistema: " + e.getMessage();
        }
    }

    /**
     * Genera el reporte de mutaciones de un paciente específico.
     *
     * @param documento Documento del paciente.
     * @return Reporte de mutaciones o mensaje de error.
     */
    private String procesarMutaciones(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return "[ERROR] El documento del paciente es obligatorio para el reporte.";
        }

        String reporte = muestraService.generarReporteMutaciones(documento);

        return (reporte != null && !reporte.isEmpty())
                ? reporte
                : "[ERROR] No se encontraron muestras o mutaciones para el paciente: " + documento;
    }

}