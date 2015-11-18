package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ClientThread;

/**
 * Created by Andreas on 31.10.2015.
 */
public class PongFromClientPacket extends AbstractClientPacket {
    private long timestamp;

    public PongFromClientPacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        this.timestamp = Long.parseLong(messageString.split(" ")[1]);
    }

    @Override
    public void process() {
        // TODO
    }
}
