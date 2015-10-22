package bai4_rn.praktikum_01.mail;

import bai4_rn.praktikum_01.client.ClientData;

/**
 * Created by Andreas on 22.10.2015.
 */
public class MailProcessorFactory {
    private MailProcessorFactory() {
    }

    public static MailProcessor createMailProcessor(ClientData clientData) {
        return MailProcessorImpl.create(clientData);
    }
}
