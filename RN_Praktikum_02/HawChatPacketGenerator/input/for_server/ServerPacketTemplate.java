package de.haw_chat.server.network.packets.server_packets;

import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class #{OPERATION_NAME}Packet extends AbstractServerPacket {
    #{ATTRIBUTES}

    public #{OPERATION_NAME}Packet(#{CONSTRUCTOR_PARAMETERS}) {
        #{CONSTRUCTOR_INIT_ATTRIBUTES}
    }

    @Override
    public String toMessageString() {
        return #{MESSAGE_STRING};
    }
}
