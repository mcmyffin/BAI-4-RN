package de.haw_chat.server.network.packets.server_packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class MessageSendedPacket extends AbstractServerPacket {
    private String username;
    private String message;
    private int timestamp;

    public MessageSendedPacket(String username, String message, int timestamp) {
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + username + " " + message + " " + timestamp;
    }
}
