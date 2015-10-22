package bai4_rn.praktikum_01.mail;

import bai4_rn.praktikum_01.client.ClientData;
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


        // TODO
    }


}
