package de.haw_chat.client.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;
import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomCreatePacket extends AbstractClientPacket {
    private String chatroomName;
    private int maxUserCount;
    private String chatroomPassword;

    public ChatroomCreatePacket(String chatroomName, int maxUserCount, String chatroomPassword) {
        this.chatroomName = chatroomName;
        this.maxUserCount = maxUserCount;
        this.chatroomPassword = chatroomPassword;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + chatroomName + " " + maxUserCount + " " + chatroomPassword;
    }
}
