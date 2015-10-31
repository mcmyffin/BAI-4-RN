package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ChatClientThread;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomChangeNamePacket extends AbstractClientPacket {
    private String chatroomNameOld;
    private String chatroomNameNew;

    public ChatroomChangeNamePacket(ChatClientThread chatClientThread, String messageString) {
        super(chatClientThread);
        this.chatroomNameOld = messageString.split(" ")[0];
        this.chatroomNameNew = messageString.split(" ")[1];
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
