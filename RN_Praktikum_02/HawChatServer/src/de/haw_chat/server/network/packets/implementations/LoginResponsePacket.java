package de.haw_chat.server.network.packets.implementations;

import de.haw_chat.server.network.packets.interfaces.ServerPacket;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LoginResponsePacket implements ServerPacket {
    private int statusCode;

    public LoginResponsePacket(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toMessageString() {
        int operationCode = getOperationData("LoginResponse").getOperationCode();
        return "" + operationCode + " " + statusCode;
    }
}
