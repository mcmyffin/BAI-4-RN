package de.haw_chat.server.network.packets.server_packets;

import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;
import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomMemberListBeginPacket extends AbstractServerPacket {
    private String chatroomName;
    private int chatroomMemberCount;

    public ChatroomMemberListBeginPacket(String chatroomName, int chatroomMemberCount) {
        this.chatroomName = chatroomName;
        this.chatroomMemberCount = chatroomMemberCount;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + chatroomName + " " + chatroomMemberCount;
    }
}
