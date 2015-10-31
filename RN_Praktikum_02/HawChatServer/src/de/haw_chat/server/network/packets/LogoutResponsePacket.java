package de.haw_chat.server.network.packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LogoutResponsePacket extends AbstractServerPacket {
    private int statusCode;

    public LogoutResponsePacket(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + statusCode;
    }
}
