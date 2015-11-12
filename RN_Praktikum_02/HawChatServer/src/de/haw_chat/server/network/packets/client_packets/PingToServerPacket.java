package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.PongFromServerPacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class PingToServerPacket extends AbstractClientPacket {
    private long timestamp;

    public PingToServerPacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        this.timestamp = Long.parseLong(messageString.split(" ")[1]);
    }

    @Override
    public void process() {

        PongFromServerPacket packet = new PongFromServerPacket(this.timestamp);
        getClientThread().writeToClient(packet);
    }
}
