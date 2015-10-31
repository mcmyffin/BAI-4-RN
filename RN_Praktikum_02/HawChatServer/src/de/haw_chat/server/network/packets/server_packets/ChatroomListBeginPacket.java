package de.haw_chat.server.network.packets.server_packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomListBeginPacket extends AbstractServerPacket {
    private int chatroomCount;

    public ChatroomListBeginPacket(int chatroomCount) {
        this.chatroomCount = chatroomCount;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + chatroomCount;
    }
}
