package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ChatClientThread;

/**
 * Created by Andreas on 31.10.2015.
 */
public class #{OPERATION_NAME}Packet extends AbstractClientPacket {
    #{ATTRIBUTES}

    public #{OPERATION_NAME}Packet(ChatClientThread chatClientThread, String messageString) {
        super(chatClientThread);
        #{INIT_ATTRIBUTES}
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
