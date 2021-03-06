package de.haw_chat.client.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;
import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LogoutPacket extends AbstractClientPacket {


    public static boolean requestedLogout = false;

    public LogoutPacket() {
        requestedLogout = true;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode();
    }
}