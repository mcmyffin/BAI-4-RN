package bai4_rn.praktikum_01.command;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.mail.Mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 22.10.2015.
 */
public enum ClientResponseEnum implements ClientResponse {
    HELO {
        @Override
        public ServerReply process() throws IOException {

            ClientData clientData = CommandUtils.getClientData();
            clientData.writeToServer("HELO");

            String serverReply = clientData.readFromServer();
            return CommandUtils.createServerReply(serverReply);
        }
    },

    EHLO {
        @Override
        public ServerReply process() {
            throw new UnsupportedOperationException();
        }
    },

    AUTH {
        @Override
        public ServerReply process() {

            ClientData clientData = CommandUtils.getClientData();
//            clientData.
            throw new UnsupportedOperationException();
        }
    },

    MAIL {
        @Override
        public ServerReply process() {
            throw new UnsupportedOperationException();
        }
    },

    RCPT {
        @Override
        public ServerReply process() {
            throw new UnsupportedOperationException();
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

            String from = "From: "+mail.getMailFromAdress();
            String to = "To: "+mail.getMailToAdress();
            String subject = "Subject: "+mail.getSubject();
            String mimeVersion = "MIME-Version: 1.0";
            String ContentTransferEncoding = "Content-Transfer-Encoding: base64";
            String ContentType = "Content-Type: "+mail.getAttachmentContentType();
            String encodedDataLimiter = "base64 encoded data";

            // write Data Header
            clientData.writeToServer(from);
            clientData.writeToServer(to);
            clientData.writeToServer(subject);
            clientData.writeToServer(mimeVersion);
            clientData.writeToServer(ContentTransferEncoding);
            clientData.writeToServer(ContentType);

            // write base64 limiter
            clientData.writeToServer(encodedDataLimiter);

            // write base64 coded File
            clientData.writeToServer(mail.getAttachmentFile());

            // write base64 limiter
            clientData.writeToServer(encodedDataLimiter);

            // write end Command
            clientData.writeToServer("\r\n.");

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
