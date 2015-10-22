package bai4_rn.praktikum_01;

import java.io.IOException;

import static bai4_rn.praktikum_01.util.FileUtils.decodeFile;
import static bai4_rn.praktikum_01.util.FileUtils.encodeFile;

/**
 * Created by Andreas on 22.10.2015.
 */
public class MailFile {
    public static void test() {
        try {
            String encodedFile = encodeFile("...");
            byte[] bytes = decodeFile(encodedFile);
            String fileData = new String(bytes);
            System.out.println(fileData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //test();
    }
}
