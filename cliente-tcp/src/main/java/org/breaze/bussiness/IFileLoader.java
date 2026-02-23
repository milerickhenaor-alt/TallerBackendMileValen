package bussiness;

public interface IFileLoader {
    /**
     * Carga el contenido de un archivo dada su ubicación.
     * @param path Ruta o identificador del recurso.
     * @return Contenido del archivo como String.
     */
    String load(String path);
}