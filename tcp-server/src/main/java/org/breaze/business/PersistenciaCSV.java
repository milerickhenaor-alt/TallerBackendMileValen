package org.breaze.business;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersistenciaCSV implements IPersistenciaTexto{


    @Override
    public List<String> listarArchivosEnRuta(String ruta, String extension) {
        File carpeta = new File(ruta);
        if (!carpeta.exists() || !carpeta.isDirectory()) return new ArrayList<>();

        String[] lista = carpeta.list((dir, name) -> name.endsWith(extension));
        return (lista == null) ? new ArrayList<>() : Arrays.asList(lista);
    }

    @Override
    public void guardarLinea(String ruta, String linea) throws IOException {

        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        try (FileWriter fw = new FileWriter(archivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

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
                lineas.add(linea);
            }
        }

        return lineas;
    }

}