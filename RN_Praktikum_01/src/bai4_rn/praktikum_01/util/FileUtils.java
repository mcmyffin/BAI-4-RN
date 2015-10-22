package bai4_rn.praktikum_01.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.base.Preconditions.*;

/**
 * Created by Andreas on 22.10.2015.
 */
public class FileUtils {
    private FileUtils() {
    }

    public static String encodeFile(String filePath) throws IOException {
        checkNotNull(filePath);

        Path path = Paths.get(filePath);
        byte[] binaryData = Files.readAllBytes(path);
        return Base64.encode(binaryData);
    }

    public static byte[] decodeFile(String encodedFile) {
        return Base64.decode(encodedFile);
    }
}
