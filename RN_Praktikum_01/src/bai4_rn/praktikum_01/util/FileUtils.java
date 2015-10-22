package bai4_rn.praktikum_01.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Andreas on 22.10.2015.
 */
public class FileUtils {
    private FileUtils() {
    }

    /**
     * Generate the MIME type of a file
     * @param filePath file for which MIME type is requested
     * @return the MIME type of file
     * @throws IOException if file was found or is not readable
     */
    public static String getContentType(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.probeContentType(path);
    }
}
