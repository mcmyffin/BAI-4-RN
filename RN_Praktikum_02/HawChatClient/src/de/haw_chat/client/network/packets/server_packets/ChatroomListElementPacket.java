package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomListElementPacket extends AbstractServerPacket {
    private String chatroomName;
    private int currentUserCount;
    private int maxUserCount;

    public ChatroomListElementPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.chatroomName = messageString.split(" ")[1];
        this.currentUserCount = Integer.parseInt(messageString.split(" ")[2]);
        this.maxUserCount = Integer.parseInt(messageString.split(" ")[3]);
    }

    @Override
    public void process() {
        getClientData().getMainController().processChatroomOverviewElement(chatroomName);
    }
}
