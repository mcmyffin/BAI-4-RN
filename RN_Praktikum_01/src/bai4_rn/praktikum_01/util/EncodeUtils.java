package bai4_rn.praktikum_01.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 22.10.2015.
 */
public class EncodeUtils {
    private EncodeUtils() {
    }

    /**
     * Encodes an file with Base64
     * @param filePath file to encode
     * @return encoded file data as String object
     * @throws IOException if file was found or is not readable
     */
    public static String encodeFile(String filePath) throws IOException {
        checkNotNull(filePath);

        Path path = Paths.get(filePath);
        byte[] binaryData = Files.readAllBytes(path);
        return Base64.encode(binaryData);
    }

    /**
     * Encodes an String with Base64
     * @param string String object to encode
     * @return encoded String data as String object
     */
    public static String encodeString(String string) {
        return Base64.encode(string.getBytes());
    }

    /**
     * Decoded file data with Base64
     * @param encodedFile encoded file data
     * @return decoded file data as byte array
     */
    public static byte[] decodeFile(String encodedFile) {
        checkNotNull(encodedFile);
        return Base64.decode(encodedFile);
    }
}
