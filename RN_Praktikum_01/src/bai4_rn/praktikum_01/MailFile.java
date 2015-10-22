package bai4_rn.praktikum_01;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.client.ClientDataFactory;

import java.io.IOException;

import static bai4_rn.praktikum_01.util.FileUtils.decodeFile;
import static bai4_rn.praktikum_01.util.FileUtils.encodeFile;

/**
 * Created by Andreas on 22.10.2015.
 */
public class MailFile {
    public static void test() {
        try {
//            String encodedFile = encodeFile("...");
//            byte[] bytes = decodeFile(encodedFile);
//            String fileData = new String(bytes);
//            System.out.println(fileData);

            String mailAddress = "rnp2015@informatik.haw-hamburg.de";
            String username = "rnp2015";
            String password = "Aufgabe1";
            String hostname = "mailgate.informatik.haw-hamburg.de";
            int port = 25;

            ClientData clientData = ClientDataFactory.create(mailAddress, username, password, hostname, port);
            clientData.writeToServer("Test");
            String s = clientData.readFromServer();
            System.out.println(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test();
    }
}
