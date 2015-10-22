package bai4_rn.praktikum_01.command;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.mail.Mail;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Andreas on 22.10.2015.
 */
public class CommandUtils {

    private static Mail mail;
    private static ClientData clientData;

    public static Mail getMail() {

        checkNotNull(mail);
        return mail;
    }

    public static void setMail(Mail aMail) {

        checkNotNull(aMail);
        mail = aMail;
    }

    public static ClientData getClientData() {

        checkNotNull(clientData);
        return clientData;
    }

    public static void setClientData(ClientData aClientData) {

        checkNotNull(aClientData);
        clientData = aClientData;
    }
    
    public static ClientResponse createClientResponse(String type) {
        checkNotNull(type);
        return ClientResponseEnum.valueOf(type);
    }

    public static ServerReply createServerReply(String inputLine) {
        checkNotNull(inputLine);
        String statusCodeStr = inputLine.substring(0,3);

        int statusCodeInt =  Integer.parseInt(statusCodeStr);
        for (ServerReplyEnum elem : ServerReplyEnum.values()) {

            if(elem.getStatusCode() == statusCodeInt) return elem;
        }

        checkState(false);
        return null;
    }
}
