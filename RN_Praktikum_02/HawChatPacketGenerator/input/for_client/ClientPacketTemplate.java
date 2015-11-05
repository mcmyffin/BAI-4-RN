package de.haw_chat.client.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;
import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;

/**
 * Created by Andreas on 31.10.2015.
 */
public class #{OPERATION_NAME}Packet extends AbstractClientPacket {
    #{ATTRIBUTES}

    public #{OPERATION_NAME}Packet(#{CONSTRUCTOR_PARAMETERS}) {
        #{CONSTRUCTOR_INIT_ATTRIBUTES}
    }

    @Override
    public String toMessageString() {
        return #{MESSAGE_STRING};
    }
}
