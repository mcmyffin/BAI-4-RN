package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomListBeginPacket extends AbstractServerPacket {
    private int chatroomCount;

    public ChatroomListBeginPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.chatroomCount = Integer.parseInt(messageString.split(" ")[1]);
    }

    @Override
    public void process() {
        // TODO: Implement processing logic
        
        // NOTES:
        // - you can access client data with: getClientData()
        // - you can access global server data with: getServerData()
        // - you can send response to client with: sendToClient(ServerPacket)
        throw new UnsupportedOperationException();
    }
}
