package org.breaze.business;

import org.breaze.excepciones.PacienteDuplicadoException;

import java.util.List;

public class BioGuardMessageProcessor implements IMessageProcessor {

    private final IPacienteService pacienteService;
    private final IVirusService virusService;
    private final IMuestraService muestraService;
    private final IAnalizadorGenetico analizador;

    private final IBioGuardFactory factory; // Única interfaz de creación
    private final IPacienteReport pacienteReport;

    public BioGuardMessageProcessor(
            IPacienteService pacienteService,
            IVirusService virusService,
            IMuestraService muestraService,
            IAnalizadorGenetico analizador,
            IBioGuardFactory factory,
            IPacienteReport pacienteReport) { // Inyectado
        this.pacienteService = pacienteService;
        this.virusService = virusService;
        this.muestraService = muestraService;
        this.analizador = analizador;
        this.factory = factory;
        this.pacienteReport = pacienteReport;
    }

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

    // --- MÉTODOS DE APOYO (Delegando responsabilidades) ---

    private String procesarRegistro(String datos) throws PacienteDuplicadoException {
        // Delegamos la creación a la factory
        Paciente p = factory.crearPaciente(datos);
        pacienteService.registrarPaciente(p);
        return "[OK] Paciente registrado exitosamente.";
    }

    private String procesarRegistroVirus(String contenidoFasta) {
        try {
            // La fábrica "entiende" el formato FASTA que el cliente envió
            Virus nuevoVirus = factory.crearVirus(contenidoFasta);

            if (nuevoVirus == null) return "[ERROR] Formato FASTA inválido";

            // El servicio lo valida (ADN) y lo guarda en la carpeta del SERVIDOR
            virusService.registrarVirus(nuevoVirus);

            return "[OK] Virus '" + nuevoVirus.getNombre() + "' cargado y almacenado en el servidor.";
        } catch (Exception e) {
            return "[ERROR] " + e.getMessage();
        }
    }

    private String procesarConsulta(String documento) {
        Paciente p = pacienteService.buscarPaciente(documento);
        if (p == null) return "[ERROR] Paciente no encontrado";

        return "[OK] " + p.toTextLine();
    }

    private String procesarAnalisis(String contenidoFasta) {
        try {
            // 1. La Factory entiende el FASTA: ">123|2026-02-22\nATCG..."
            MuestraADN muestra = factory.crearMuestra(contenidoFasta);

            // 2. El servicio guarda la muestra en carpetas por ID y valida ADN
            muestraService.registrarMuestras(muestra);

            // 3. Obtenemos el catálogo de virus para comparar
            List<Virus> catalogo = virusService.cargarTodosLosVirus();

            // 4. El Analizador Genético (Experto en algoritmos) busca coincidencias
            List<ResultadoDiagnostico> resultados = analizador.realizarDiagnostico(
                    muestra.getSecuencia(),
                    catalogo
            );

            // 5. El servicio guarda el resultado en el CSV solicitado dentro de la carpeta del paciente
            muestraService.generarDiagnosticoCSV(muestra, resultados);

            return "[OK] Análisis completado. Diagnóstico generado en el servidor.";
        } catch (Exception e) {
            return "[ERROR] " + e.getMessage();
        }
    }


    private String procesarReportePacientesRiesgo() {
        try {
            return pacienteReport.generarReporte();
        } catch (Exception e) {
            return "[ERROR] Sistema: " + e.getMessage();
        }
    }

    private String procesarMutaciones(String documento) {
        // Validamos que el documento no sea nulo o vacío antes de procesar
        if (documento == null || documento.trim().isEmpty()) {
            return "[ERROR] El documento del paciente es obligatorio para el reporte.";
        }

        // El servicio se encarga de buscar las muestras y comparar secuencias
        String reporte = muestraService.generarReporteMutaciones(documento);

        return (reporte != null && !reporte.isEmpty())
                ? reporte
                : "[ERROR] No se encontraron muestras o mutaciones para el paciente: " + documento;
    }

}