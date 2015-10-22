package bai4_rn.praktikum_01.mail;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.command.ClientResponse;
import bai4_rn.praktikum_01.command.ClientResponseEnum;
import bai4_rn.praktikum_01.command.CommandUtils;
import bai4_rn.praktikum_01.command.ServerReply;

import java.io.IOException;

import static com.google.common.base.Preconditions.*;

/**
 * Created by abp615 on 22.10.2015.
 */
public class MailProcessorImpl implements MailProcessor {
    private ClientData clientData;


    public MailProcessorImpl(ClientData clientData) {
        checkNotNull(clientData);
        this.clientData = clientData;
    }

    public void error(String message) {
        checkState(false);
    }

    @Override
    public void run() {
        ClientResponse clientResponse = null;
        ServerReply serverReply = null;

        try {
            String message = clientData.readFromServer();
            serverReply = CommandUtils.createServerReply(message);
            if (serverReply.getStatusCode() != 220)
                error("Service is not ready!");

            clientResponse = CommandUtils.createClientResponse("HELO");
            serverReply = clientResponse.process();
            if (serverReply.getStatusCode() != 250)
                error("Invalid request!");

            clientResponse = CommandUtils.createClientResponse("AUTH_START");
            serverReply = clientResponse.process();
            if (serverReply.getStatusCode() != 334)
                error("Authentication failed!");

            clientResponse = CommandUtils.createClientResponse("AUTH_USERNAME");
            serverReply = clientResponse.process();
            if (serverReply.getStatusCode() != 334)
                error("Authentication failed!");

            clientResponse = CommandUtils.createClientResponse("AUTH_PASSWORD");
            serverReply = clientResponse.process();
            if (serverReply.getStatusCode() != 235)
                error("Authentication failed!");

            clientResponse = CommandUtils.createClientResponse("MAIL");
            serverReply = clientResponse.process();
            if (serverReply.getStatusCode() != 250)
                error("Invalid request!");

            clientResponse = CommandUtils.createClientResponse("RCPT");
            serverReply = clientResponse.process();
            if (serverReply.getStatusCode() != 250)
                error("Invalid request!");

            clientResponse = CommandUtils.createClientResponse("DATA_START");
            serverReply = clientResponse.process();
            if (serverReply.getStatusCode() != 354)
                error("Input could not be started!");

            clientResponse = CommandUtils.createClientResponse("DATA");
            serverReply = clientResponse.process();
            if (serverReply.getStatusCode() != 250)
                error("Invalid request!");

            clientResponse = CommandUtils.createClientResponse("QUIT");
            serverReply = clientResponse.process();
            if (serverReply.getStatusCode() != 221)
                error("Service could not be closed!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new UnsupportedOperationException();
    }
}
