package bussiness;

/**
 * Define el contrato para la carga de archivos desde
 * diferentes fuentes (disco, red, memoria, etc.).
 */
public interface IFileLoader {

    /**
     * Carga el contenido de un archivo dada su ubicación.
     *
     * @param path Ruta o identificador del recurso.
     * @return Contenido del archivo como String.
     */
    String load(String path);
}