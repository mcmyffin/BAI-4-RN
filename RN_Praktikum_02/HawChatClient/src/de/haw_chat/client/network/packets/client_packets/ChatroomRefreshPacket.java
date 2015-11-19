package de.haw_chat.client.network.packets.client_packets;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomRefreshPacket extends AbstractClientPacket {
    private String chatroom;

    public ChatroomRefreshPacket(String chatroom) {
        this.chatroom = chatroom;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + chatroom;
    }
}
