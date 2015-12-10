package de.haw_chat.client.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;
import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomJoinPacket extends AbstractClientPacket {
    private String chatroomName;
    private String chatroomPassword;

    public ChatroomJoinPacket(String chatroomName, String chatroomPassword) {
        this.chatroomName = chatroomName;
        this.chatroomPassword = chatroomPassword;
    }

    @Override
    public String toMessageString() {
        if (chatroomPassword == null || chatroomPassword.isEmpty())
            return "" + getOperationCode() + " " + chatroomName;
        else
            return "" + getOperationCode() + " " + chatroomName + " " + chatroomPassword;
    }
}
