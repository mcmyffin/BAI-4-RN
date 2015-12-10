package de.haw_chat.server.network.packets.server_packets;

import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;
import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomNameChangedPacket extends AbstractServerPacket {
    private String chatroomNameOld;
    private String chatroomNameNew;

    public ChatroomNameChangedPacket(String chatroomNameOld, String chatroomNameNew) {
        this.chatroomNameOld = chatroomNameOld;
        this.chatroomNameNew = chatroomNameNew;
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode() + " " + chatroomNameOld + " " + chatroomNameNew;
    }
}
