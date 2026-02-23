package bussiness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DiskFileLoader implements IFileLoader {

    @Override
    public String load(String path) {
        StringBuilder sb = new StringBuilder();
        File file = new File(path);

        if (!file.exists()) return "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudo leer el archivo local: " + e.getMessage());
            return "";
        }

        return sb.toString().trim();
    }
}