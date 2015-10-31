package de.haw_chat.server.network.packets;

import de.haw_chat.server.network.interfaces.ChatClientThread;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UsernameChangePacket extends AbstractClientPacket {
    private String username;

    public UsernameChangePacket(ChatClientThread chatClientThread, String messageString) {
        super(chatClientThread);
        this.username = messageString.split(" ")[0];
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
