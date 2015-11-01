package de.haw_chat.client.network.packets.client_packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class RegisterAccountPacket extends AbstractClientPacket {
    private String username;
    private String password;

    public RegisterAccountPacket(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + username + " " + password;
    }
}
