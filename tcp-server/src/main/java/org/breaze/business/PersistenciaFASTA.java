package org.breaze.business;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaFASTA implements IPersistenciaTexto {

    @Override
    public void guardarLinea(String ruta, String linea) throws IOException {
        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        // Usamos try-with-resources para asegurar el cierre de flujos
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            // Si la línea no empieza con '>', asumimos que es una secuencia y
            // opcionalmente podríamos formatearla, pero respetaremos lo que llegue.
            out.println(linea);
        }
    }

    @Override
    public List<String> leerLineas(String ruta) throws IOException {
        List<String> lineas = new ArrayList<>();
        File file = new File(ruta);

        if (!file.exists()) return lineas;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    lineas.add(linea);
                }
            }
        }
        return lineas;
    }

    // Nuevo método necesario para que el servicio no use la clase File
    @Override
    public List<String> listarArchivosEnRuta(String ruta, String extension) {
        List<String> nombres = new ArrayList<>();
        File carpeta = new File(ruta);
        if (carpeta.exists() && carpeta.isDirectory()) {
            File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(extension));
            if (archivos != null) {
                for (File f : archivos) nombres.add(f.getName());
            }
        }
        return nombres;
    }
}