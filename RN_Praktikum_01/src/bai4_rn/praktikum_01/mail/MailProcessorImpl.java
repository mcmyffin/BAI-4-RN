package bai4_rn.praktikum_01.mail;

import bai4_rn.praktikum_01.client.ClientData;

/**
 * Created by abp615 on 22.10.2015.
 */
public class MailProcessorImpl implements MailProcessor {

    private ClientData clientData;

    public MailProcessorImpl(ClientData data) {

        this.clientData = clientData;
    }

    @Override
    public void run() {

        // TODO
    }
}
