package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ChatClientThread;
import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomDeletePacket extends AbstractClientPacket {
    private String chatroomName;

    public ChatroomDeletePacket(ChatClientThread chatClientThread, String messageString) {
        super(chatClientThread);
        this.chatroomName = messageString.split(" ")[1];
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
