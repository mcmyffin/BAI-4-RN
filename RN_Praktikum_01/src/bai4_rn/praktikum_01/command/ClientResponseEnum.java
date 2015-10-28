package bai4_rn.praktikum_01.command;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.mail.Mail;
import bai4_rn.praktikum_01.util.EncodeUtils;
import bai4_rn.praktikum_01.util.FileUtils;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 22.10.2015.
 */
public enum ClientResponseEnum implements ClientResponse {
    HELO {
        @Override
        public ServerReply process() throws IOException {

            ClientData clientData = CommandUtils.getClientData();
            clientData.writeToServer("HELO " + clientData.getHostname());

            String serverReply = clientData.readFromServer();
            return CommandUtils.createServerReply(serverReply);
        }
    },

    EHLO {
        @Override
        public ServerReply process() throws IOException {

            ClientData clientData = CommandUtils.getClientData();
            clientData.writeToServer("EHLO " + clientData.getHostname());

            clientData.readFromServer();
            String serverReply = clientData.readFromServer();
            return CommandUtils.createServerReply(serverReply);
        }
    },

    AUTH_START {
        @Override
        public ServerReply process() throws IOException {

            ClientData clientData = CommandUtils.getClientData();

            String loginDATA = EncodeUtils.encodeString("\0"+clientData.getUsername()+"\0"+clientData.getPassword());
            clientData.writeToServer("AUTH PLAIN "+loginDATA);
            String serverReply = clientData.readFromServer();

            return CommandUtils.createServerReply(serverReply);
        }
    },

    AUTH_USERNAME {
        @Override
        public ServerReply process() throws IOException {

            ClientData clientData = CommandUtils.getClientData();
            String loginBase64Data = EncodeUtils.encodeString(clientData.getMailAddress());

            clientData.writeToServer(loginBase64Data);
            String serverReply = clientData.readFromServer();

            return CommandUtils.createServerReply(serverReply);
        }
    },

    AUTH_PASSWORD {
        @Override
        public ServerReply process() throws IOException {

            ClientData clientData = CommandUtils.getClientData();
            String loginBase64Data = EncodeUtils.encodeString(clientData.getPassword());

            clientData.writeToServer(loginBase64Data);
            String serverReply = clientData.readFromServer();

            return CommandUtils.createServerReply(serverReply);
        }
    },

    MAIL {
        @Override
        public ServerReply process() throws IOException {

            ClientData clientData = CommandUtils.getClientData();
            clientData.writeToServer("MAIL FROM: "+clientData.getMailAddress());

            String serverReply = clientData.readFromServer();
            return CommandUtils.createServerReply(serverReply);
        }
    },

    RCPT {
        @Override
        public ServerReply process() throws IOException {

            ClientData clientData = CommandUtils.getClientData();
            Mail mail = CommandUtils.getMail();

            clientData.writeToServer("RCPT TO: "+mail.getMailToAdress());

            String serverReply = clientData.readFromServer();
            return CommandUtils.createServerReply(serverReply);
        }
    },

    DATA_START {

        @Override
        public ServerReply process() throws IOException {
            ClientData clientData = CommandUtils.getClientData();

            clientData.writeToServer("DATA");
            String serverReply = clientData.readFromServer();

            return CommandUtils.createServerReply(serverReply);
        }
    },

    DATA {
        @Override
        public ServerReply process() throws IOException {

            ClientData clientData = CommandUtils.getClientData();
            Mail mail = CommandUtils.getMail();

            // header
            String from = "From: "+mail.getMailFromAdress();
            String to = "To: "+mail.getMailToAdress();
            String subject = "Subject: "+mail.getSubject();
            String mimeVersion = "MIME-Version: 1.0";
            String boundary = Long.toString(System.nanoTime());
            String contentType_header = "Content-Type: "+"multipart/mixed; boundary="+boundary;

            // attachment header
            String contenType_attachment = "Content-Disposition: attachment; filename="+mail.getFileName()+";";
            String contentTransferEncoding = "Content-Transfer-Encoding: base64";
            String encodedDataLimiter = "base64 encoded data";

            // ---------------------- write data ---------------------------
            // Data Header
            clientData.writeToServer(from);
            clientData.writeToServer(to);
            clientData.writeToServer(subject);
            clientData.writeToServer(mimeVersion);
            clientData.writeToServer(contentType_header);
            clientData.writeToServerNewLine();

            // new Data Header for attachment
            clientData.writeToServer("--"+boundary);
            clientData.writeToServer(contenType_attachment);
            clientData.writeToServer(contentTransferEncoding);
            clientData.writeToServerNewLine();

            // write base64 encoded File
            clientData.writeToServer(mail.getAttachmentFile());
            clientData.writeToServer("--"+boundary+"--");

            // write end of Command
            clientData.writeToServer(".");

            String serverReply = clientData.readFromServer();
            return CommandUtils.createServerReply(serverReply);
        }
    },

    QUIT {
        @Override
        public ServerReply process() throws IOException {

            ClientData clientData = CommandUtils.getClientData();
            clientData.writeToServer("QUIT");

            String serverReply = clientData.readFromServer();
            return CommandUtils.createServerReply(serverReply);
        }
    };

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String toString() {
        return "ClientResponse{" +
                "name='" + getName() + '\'' +
                '}';
    }
}
