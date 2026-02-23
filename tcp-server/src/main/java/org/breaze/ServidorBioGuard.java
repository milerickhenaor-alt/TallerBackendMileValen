package org.breaze;

import org.breaze.business.*;
import org.breaze.common.*;
import org.breaze.network.*;

public class ServidorBioGuard {

    public static void main(String[] args) {
        System.out.println("=== SISTEMA BIOGUARD v2.0 - 2026 ===");

        try {
            // 1. INFRAESTRUCTURA
            IConfigReader reader = new PropertiesManager("application.properties");
            ISSLConfig tcpConfig = new TCPConfig(reader);

            // 2. PERSISTENCIA
            IPersistenciaTexto persistenciaCsv = new PersistenciaCSV();
            IPersistenciaTexto persistenciaFasta = new PersistenciaFASTA();

            // 3. COMPONENTES LÓGICOS Y FÁBRICAS
            IAnalizadorMutaciones analizadorMutaciones = new AnalizadorMutacionesADN();
            IAnalizadorGenetico analizadorGenetico = new AnalizadorGenetico();
            IBioGuardFactory factory = new BioGuardFactory();

            // 4. SERVICIOS DE NEGOCIO
            IPacienteService pacienteService = new PacienteService(persistenciaCsv);
            // El VirusService es necesario para el RiskAnalyzer más adelante
            VirusService virusService = new VirusService(persistenciaFasta, analizadorMutaciones);
            IMuestraService muestraService = new MuestraService(persistenciaFasta, analizadorMutaciones);

            // 5. COMPONENTES DE REPORTE (Siguiendo el grafo de dependencias)
            // Primero el Analyzer de riesgo, que depende del servicio de virus
            IPacienteRiskAnalyzer riskAnalyzer = new PacienteRiskAnalyzer(virusService);

            // Luego el generador de reportes, que depende del Analyzer
            IPacienteReport report = new PacientesReport(riskAnalyzer);

            // 6. PROCESADOR DE MENSAJES (Orquestador final)
            // Inyectamos todas las dependencias necesarias, incluido el nuevo reporte
            IMessageProcessor processor = new BioGuardMessageProcessor(
                    pacienteService,
                    virusService,
                    muestraService,
                    analizadorGenetico,
                    factory,
                    report // Inyectamos la interfaz del reporte aquí
            );

            // 7. LANZAMIENTO
            INetworkService server = new SSLTCPServer(tcpConfig, processor);

            System.out.println("[INFO] Servidor listo y escuchando peticiones...");
            server.start();

        } catch (Exception e) {
            System.err.println("[FATAL] Error crítico al iniciar el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}