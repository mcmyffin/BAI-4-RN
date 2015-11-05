package de.haw_chat.server.network.packets.server_packets;

import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;
import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomMaxUserCountChangedPacket extends AbstractServerPacket {
    private String chatroomName;
    private int maxUserCount;

    public ChatroomMaxUserCountChangedPacket(String chatroomName, int maxUserCount) {
        this.chatroomName = chatroomName;
        this.maxUserCount = maxUserCount;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + chatroomName + " " + maxUserCount;
    }
}
