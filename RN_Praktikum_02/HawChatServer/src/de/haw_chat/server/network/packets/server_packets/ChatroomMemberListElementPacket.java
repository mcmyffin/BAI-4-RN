package de.haw_chat.server.network.packets.server_packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomMemberListElementPacket extends AbstractServerPacket {
    private String chatroomName;
    private String username;

    public ChatroomMemberListElementPacket(String chatroomName, String username) {
        this.chatroomName = chatroomName;
        this.username = username;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + chatroomName + " " + username;
    }
}
