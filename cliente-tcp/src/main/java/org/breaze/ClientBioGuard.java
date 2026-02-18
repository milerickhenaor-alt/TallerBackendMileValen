import java.util.Scanner;

import org.breaze.business.Virus;
import org.breaze.network.IMessageService;
import org.breaze.network.SSLTCPClient;
import org.breaze.business.MuestraADN;
import org.breaze.common.IConfigReader;
import org.breaze.common.Paciente;
import org.breaze.common.PropertiesManager;
import org.breaze.network.ISSLConfig;
import org.breaze.network.TCPConfig;

/**
 * Clase principal del sistema BioGuard.
 *
 * Se encarga de mostrar el menú al usuario,
 * construir los mensajes según la opción elegida
 * y enviarlos al servidor.
 */
public class ClientBioGuard {

    public static void main(String[] args) {

        // Se carga la configuración desde el archivo application.properties
        IConfigReader reader = new PropertiesManager("application.properties");

        // Se crea la configuración TCP/SSL usando los valores leídos
        ISSLConfig tcpConfig = new TCPConfig(reader);

        // Se crea el cliente que enviará los mensajes al servidor
        IMessageService client = new SSLTCPClient(tcpConfig);

        // Scanner para leer datos desde consola
        Scanner sc = new Scanner(System.in);

        boolean continuar = true;

        // Bucle principal del sistema
        while (continuar){

            // Menú de opciones
            System.out.println("=== SISTEMA BIOGUARD ===");
            System.out.println("1. Registrar Paciente");
            System.out.println("2. Consultar Paciente");
            System.out.println("3. Registrar Virus (FASTA)");
            System.out.println("4. Analizar ADN");
            System.out.println("5. Reporte de Pacientes de Alto Riesgo");
            System.out.println("6. Revisar Mutación");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine(); // Limpia el buffer

            String mensaje = "";

            switch (opcion) {

                case 1:
                    // Registro de paciente
                    System.out.print("Documento: ");
                    String doc = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nom = sc.nextLine();
                    System.out.print("Apellido: ");
                    String ape = sc.nextLine();
                    System.out.print("Edad: ");
                    int edad = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Género: ");
                    String gen = sc.nextLine();
                    System.out.print("Ciudad: ");
                    String ciu = sc.nextLine();
                    System.out.print("País: ");
                    String pais = sc.nextLine();

                    // Se crea el objeto paciente
                    Paciente p = new Paciente(doc, nom, ape, edad, correo, gen, ciu, pais);

                    // Se construye el mensaje a enviar
                    mensaje = "REGISTRAR_PACIENTE:" + p.toTextLine();
                    break;

                case 2:
                    // Consulta de paciente
                    System.out.print("Documento: ");
                    String documentoConsulta = sc.nextLine();

                    mensaje = "CONSULTAR_PACIENTE:" + documentoConsulta;
                    break;

                case 3:
                    // Registro de virus
                    System.out.print("Nombre del virus: ");
                    String NombreVirus = sc.nextLine();
                    System.out.print("Nivel de infecciocidad: ");
                    String NivelInfecciocidad = sc.nextLine();
                    System.out.print("Secuencia Genetica (ATCG): ");
                    String SecuenciaGenetica = sc.nextLine();

                    Virus v = new Virus(NombreVirus, NivelInfecciocidad, SecuenciaGenetica);

                    mensaje = "REGISTRAR_VIRUS:" + v.toTextLine();
                    break;

                case 4:
                    // Análisis de ADN
                    System.out.print("Documento: ");
                    String docADN = sc.nextLine();

                    System.out.print("Fecha (yyyy-mm-dd): ");
                    String fecha = sc.nextLine();

                    System.out.print("Secuencia ADN: ");
                    String secuencia = sc.nextLine();

                    MuestraADN muestra = new MuestraADN(docADN, fecha, secuencia);

                    mensaje = "ANALIZAR_ADN:" + muestra.toTextLine();
                    break;

                case 5:
                    // Solicitud de reporte de pacientes de alto riesgo
                    mensaje = "REPORTE_PACIENTES:";
                    break;

                case 6:
                    // Solicitud de reporte de mutaciones
                    System.out.print("Documento: ");
                    String documentoConsult = sc.nextLine();
                    mensaje = "REPORTE_MUTACIONES:" + documentoConsult;
                    break;

                case 7:
                    // Salida del sistema
                    System.out.println("Cerrando conexión....");
                    continuar = false;
                    continue;

                default:
                    System.out.println("Opción no válida");
                    return;
            }

            // Se envía el mensaje al servidor
            String respuesta = client.sendMessage(mensaje);

            // (Aquí podrías mostrar la respuesta si lo deseas)
        }

    }

}
