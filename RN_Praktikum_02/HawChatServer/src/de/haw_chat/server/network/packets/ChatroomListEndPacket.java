package de.haw_chat.server.network.packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomListEndPacket extends AbstractServerPacket {
    

    public ChatroomListEndPacket() {
        
    }

    @Override
    public String toMessageString() {
        return "" + getOperationCode();
    }
}
