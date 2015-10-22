package bai4_rn.praktikum_01;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.client.ClientDataFactory;
import bai4_rn.praktikum_01.command.CommandUtils;
import bai4_rn.praktikum_01.mail.Mail;
import bai4_rn.praktikum_01.mail.MailImpl;
import bai4_rn.praktikum_01.mail.MailProcessorImpl;
import bai4_rn.praktikum_01.util.FileUtils;

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

        if (args.length == 2) {
            recipientMailAddress = args[0];
            attachmentFilePath = args[1];
        } else {
            checkArgument(false);
        }

        try {
            ClientData clientData = createClientData("config.xml");
            CommandUtils.setClientData(clientData);

            String encodedAttachment = FileUtils.encodeFile(attachmentFilePath);
            String contentType = FileUtils.getContentType(attachmentFilePath);
            Mail mail = new MailImpl(
                    clientData.getMailAddress(),
                    recipientMailAddress,
                    subject,
                    encodedAttachment,
                    contentType);
            CommandUtils.setMail(mail);

            MailProcessorImpl mailProcessor = new MailProcessorImpl(clientData);
            mailProcessor.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
