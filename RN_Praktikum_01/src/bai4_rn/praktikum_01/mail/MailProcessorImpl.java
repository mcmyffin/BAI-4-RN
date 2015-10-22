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


    public MailProcessorImpl(ClientData data) {

        checkNotNull(data);
        this.clientData = clientData;
    }

    @Override
    public void run() {
        ClientResponse clientResponse = CommandUtils.createClientResponse("HELO");
        ServerReply heloReply = clientResponse.process();

        // TODO
    }


}
