package bai4_rn.praktikum_01.mail;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.command.ClientResponse;
import bai4_rn.praktikum_01.command.CommandUtils;
import bai4_rn.praktikum_01.command.ServerReply;

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

    @Override
    public void run() {
        ClientResponse clientResponse = null;
        ServerReply serverReply = null;

        clientResponse = CommandUtils.createClientResponse("HELO");
        serverReply = clientResponse.process();

        throw new UnsupportedOperationException();
    }


}
