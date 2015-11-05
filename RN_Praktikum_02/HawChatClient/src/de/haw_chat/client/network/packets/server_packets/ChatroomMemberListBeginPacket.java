package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomMemberListBeginPacket extends AbstractServerPacket {
    private String chatroomName;
    private int chatroomMemberCount;

    public ChatroomMemberListBeginPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.chatroomName = messageString.split(" ")[1];
        this.chatroomMemberCount = Integer.parseInt(messageString.split(" ")[2]);
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
