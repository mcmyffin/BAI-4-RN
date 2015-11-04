package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;

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
        // TODO: Implement processing logic
        
        // NOTES:
        // - you can access client data with: getClientData()
        // - you can access global server data with: getServerData()
        // - you can send response to client with: sendToServer(ServerPacket)
        throw new UnsupportedOperationException();
    }
}
