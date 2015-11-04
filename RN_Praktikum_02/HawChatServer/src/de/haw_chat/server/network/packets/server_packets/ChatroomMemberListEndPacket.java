package de.haw_chat.server.network.packets.server_packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomMemberListEndPacket extends AbstractServerPacket {
    private String chatroomName;

    public ChatroomMemberListEndPacket(String chatroomName) {
        this.chatroomName = chatroomName;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + chatroomName;
    }
}
