package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.UnsupportedServerPacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UnknownClientPacket extends AbstractClientPacket {
    

    public UnknownClientPacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        
    }

    @Override
    public void process() {
        UnsupportedServerPacket packet = new UnsupportedServerPacket();
        getClientThread().writeToClient(packet);
    }
}
