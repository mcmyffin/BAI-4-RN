package bai4_rn.praktikum_01.command;

import bai4_rn.praktikum_01.client.ClientData;
import bai4_rn.praktikum_01.mail.Mail;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 22.10.2015.
 */
public class CommandUtils {
    public static ClientResponse createClientResponse(String type) {
        checkNotNull(type);
        return ClientResponseEnum.valueOf(type);
    }

    static ServerReply createServerReply(String inputLine) {
        checkNotNull(inputLine);
        throw new UnsupportedOperationException();
    }
}
