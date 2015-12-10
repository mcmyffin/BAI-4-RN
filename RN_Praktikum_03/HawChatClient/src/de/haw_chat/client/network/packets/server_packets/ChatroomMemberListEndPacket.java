package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomMemberListEndPacket extends AbstractServerPacket {
    private String chatroomName;

    public ChatroomMemberListEndPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.chatroomName = messageString.split(" ")[1];
    }

    @Override
    public void process() {
        getClientData().getMainController().processChatroomListEnd(chatroomName);
    }
}
