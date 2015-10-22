package bai4_rn.praktikum_01;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.client.ClientDataFactory;

import java.io.*;
import java.util.Properties;

import static bai4_rn.praktikum_01.util.FileUtils.decodeFile;
import static bai4_rn.praktikum_01.util.FileUtils.encodeFile;

/**
 * Created by Andreas on 22.10.2015.
 */
public class MailFile {
    public static ClientData createClientData(String configurationFile) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(configurationFile);
        properties.loadFromXML(inputStream);

        String mailAddress = properties.getProperty("mailAddress");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String hostname = properties.getProperty("hostname");
        int port = Integer.valueOf(properties.getProperty("port"));

        inputStream.close();

        ClientData clientData = ClientDataFactory.create(mailAddress, username, password, hostname, port);
        return clientData;
    }

    public static void test() {
        try {
//            String encodedFile = encodeFile("...");
//            byte[] bytes = decodeFile(encodedFile);
//            String fileData = new String(bytes);
//            System.out.println(fileData);

            ClientData clientData = createClientData("config.xml");
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
