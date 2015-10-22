package bai4_rn.praktikum_01.client;

import java.io.IOException;

/**
 * Created by Andreas on 22.10.2015.
 */
public final class ClientDataFactory {
    private ClientDataFactory() {
    }

    public static ClientData create(String mailAddress, String username, String password, String hostname, int port) throws IOException {
        return ClientDataImpl.create(mailAddress, username, password, hostname, port);
    }
}
