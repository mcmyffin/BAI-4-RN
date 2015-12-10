package de.haw_chat.server.network.packets.server_packets;

/**
 * Created by Andreas on 31.10.2015.
 */
public class InvalidPacket extends AbstractServerPacket {

    private String message;
    public InvalidPacket(String message) {
        this.message = message;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode()+" "+message;
    }
}
