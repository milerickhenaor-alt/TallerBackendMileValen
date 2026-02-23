package org.breaze.business;

import org.breaze.excepciones.VirusDuplicadoException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VirusService implements IVirusService {

    private final IPersistenciaTexto persistencia;
    private final IAnalizadorMutaciones analizador;
    private static final String CARPETA = "data/virus/";

    public VirusService(IPersistenciaTexto persistencia, IAnalizadorMutaciones analizador) {
        this.persistencia = persistencia;
        this.analizador = analizador;
    }

    @Override
    public boolean registrarVirus(Virus virus) throws VirusDuplicadoException, FormatoSecuenciaInvalidoException {
        // 1. Validar ADN (bucle manual en Analizador)
        analizador.validarFormato(virus.getSecuencia());

        // 2. Ruta interna del servidor (donde se almacenará permanentemente)
        String rutaServidor = "data/virus/" + virus.getNombre() + ".fasta";

        // 3. Reconstruir el formato para guardarlo localmente en el servidor
        String contenidoParaGuardar = ">" + virus.getNombre() + "|" + virus.getNivelInfecciosidad() + "\n"
                + virus.getSecuencia();

        try {
            // Guardamos en el disco del servidor usando la PersistenciaFASTA
            persistencia.guardarLinea(rutaServidor, contenidoParaGuardar);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Virus buscarVirus(String nombre) {
        String ruta = CARPETA + nombre + ".fasta";
        try {
            List<String> lineas = persistencia.leerLineas(ruta);

            if (lineas.size() >= 2) {
                String header = lineas.get(0);
                String secuencia = lineas.get(1);

                if (header.startsWith(">")) {
                    String datos = header.substring(1);
                    String[] partes = datos.split("\\|");
                    return new Virus(partes[0], partes[1], secuencia);
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    @Override
    public List<Virus> cargarTodosLosVirus() {
        List<Virus> lista = new ArrayList<>();

        // Usamos el método de la persistencia para listar
        List<String> nombresArchivos = persistencia.listarArchivosEnRuta(CARPETA, ".fasta");

        for (String nombreArchivo : nombresArchivos) {
            // "nombreArchivo" trae "Ebola.fasta", quitamos la extensión para buscarlo
            String nombreVirus = nombreArchivo.replace(".fasta", "");
            Virus v = buscarVirus(nombreVirus);
            if (v != null) lista.add(v);
        }

        return lista;
    }
}