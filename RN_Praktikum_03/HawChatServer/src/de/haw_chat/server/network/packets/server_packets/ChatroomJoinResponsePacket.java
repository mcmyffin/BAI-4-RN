package de.haw_chat.server.network.packets.server_packets;

import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;
import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomJoinResponsePacket extends AbstractServerPacket {
    private Status statusCode;
    private String chatName;

    public ChatroomJoinResponsePacket(Status statusCode, String chatName) {
        this.statusCode = statusCode;
        this.chatName = chatName;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + statusCode.getStatusCode() + " " +chatName ;
    }
}
