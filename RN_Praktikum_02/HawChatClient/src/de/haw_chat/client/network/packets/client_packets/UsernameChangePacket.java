package de.haw_chat.client.network.packets.client_packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UsernameChangePacket extends AbstractClientPacket {
    private String username;

    public UsernameChangePacket(String username) {
        this.username = username;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + username;
    }
}
