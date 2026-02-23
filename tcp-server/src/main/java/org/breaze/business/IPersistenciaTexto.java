package org.breaze.business;

import java.io.IOException;
import java.util.List;

public interface IPersistenciaTexto {

    List<String> listarArchivosEnRuta(String ruta, String extension);

    void guardarLinea(String ruta, String linea) throws IOException;

    List<String> leerLineas(String ruta) throws IOException;
}
