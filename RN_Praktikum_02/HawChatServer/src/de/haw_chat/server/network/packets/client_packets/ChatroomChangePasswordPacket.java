package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ClientThread;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomChangePasswordPacket extends AbstractClientPacket {
    private String chatroomName;
    private String chatroomPassword;

    public ChatroomChangePasswordPacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        this.chatroomName = messageString.split(" ")[1];
        this.chatroomPassword = messageString.split(" ")[2];
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
