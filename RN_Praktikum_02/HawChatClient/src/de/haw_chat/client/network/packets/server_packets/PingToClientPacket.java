package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class PingToClientPacket extends AbstractServerPacket {
    private int timestamp;

    public PingToClientPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.timestamp = Integer.parseInt(messageString.split(" ")[1]);
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