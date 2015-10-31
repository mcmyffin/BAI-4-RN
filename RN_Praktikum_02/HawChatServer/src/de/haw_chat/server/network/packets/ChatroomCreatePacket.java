package de.haw_chat.server.network.packets;

import de.haw_chat.server.network.interfaces.ChatClientThread;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomCreatePacket extends AbstractClientPacket {
    private String chatroomName;
    private int maxUserCount;
    private String chatroomPassword;

    public ChatroomCreatePacket(ChatClientThread chatClientThread, String messageString) {
        super(chatClientThread);
        this.chatroomName = messageString.split(" ")[0];
        this.maxUserCount = Integer.parseInt(messageString.split(" ")[1]);
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
