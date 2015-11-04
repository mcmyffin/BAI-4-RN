package de.haw_chat.server.network.packets.server_packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class PingToClientPacket extends AbstractServerPacket {
    private int timestamp;

    public PingToClientPacket(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + timestamp;
    }
}
