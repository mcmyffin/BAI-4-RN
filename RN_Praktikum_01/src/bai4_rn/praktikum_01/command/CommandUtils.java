package bai4_rn.praktikum_01.command;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.mail.Mail;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 22.10.2015.
 */
public class CommandUtils {
    public static Mail getMail() {
        throw new UnsupportedOperationException();
    }

    public static void setMail(Mail mail) {
        throw new UnsupportedOperationException();
    }

    public static ClientData getClientData() {
        throw new UnsupportedOperationException();
    }

    public static void setClientData(ClientData clientData) {
        throw new UnsupportedOperationException();
    }
    
    public static ClientResponse createClientResponse(String type) {
        checkNotNull(type);
        return ClientResponseEnum.valueOf(type);
    }

    static ServerReply createServerReply(String inputLine) {
        checkNotNull(inputLine);
        throw new UnsupportedOperationException();
    }
}
