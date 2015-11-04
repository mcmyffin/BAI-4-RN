package de.haw_chat.client.network.packets.client_packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class PingToServerPacket extends AbstractClientPacket {
    private int timestamp;

    public PingToServerPacket(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + timestamp;
    }
}
