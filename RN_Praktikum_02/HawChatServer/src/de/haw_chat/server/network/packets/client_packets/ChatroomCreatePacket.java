package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ChatClientThread;

import static de.haw_chat.common.operation.implementations.StatusEnum.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomCreatePacket extends AbstractClientPacket {
    private String chatroomName;
    private int maxUserCount;
    private String chatroomPassword;

    public ChatroomCreatePacket(ChatClientThread chatClientThread, String messageString) {
        super(chatClientThread);
        this.chatroomName = messageString.split(" ")[1];
        this.maxUserCount = Integer.parseInt(messageString.split(" ")[2]);
        this.chatroomPassword = messageString.split(" ")[3];
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
