package bai4_rn.praktikum_01;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.client.ClientDataFactory;
import bai4_rn.praktikum_01.command.CommandUtils;
import bai4_rn.praktikum_01.mail.*;
import bai4_rn.praktikum_01.util.EncodeUtils;
import bai4_rn.praktikum_01.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkArgument;

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

    public static void main(String[] args) {
        String recipientMailAddress = null;
        String attachmentFilePath = null;
        String subject = "Im sending that file!";
        File file;

        if (args.length == 2) {
            recipientMailAddress = args[0];
            attachmentFilePath = args[1];
        } else {
            checkArgument(false);
        }

        try {
            ClientData clientData = createClientData("config.xml");
            CommandUtils.setClientData(clientData);
            file = new File(attachmentFilePath);

            String encodedAttachment = EncodeUtils.encodeFile(attachmentFilePath);
            String contentType = FileUtils.getContentType(attachmentFilePath);
            Mail mail = MailFactory.createMail(
                    clientData.getMailAddress(),
                    recipientMailAddress,
                    subject,
                    encodedAttachment,
                    contentType,file.getName());
            CommandUtils.setMail(mail);

            MailProcessor mailProcessor = MailProcessorFactory.createMailProcessor(clientData);
            mailProcessor.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
