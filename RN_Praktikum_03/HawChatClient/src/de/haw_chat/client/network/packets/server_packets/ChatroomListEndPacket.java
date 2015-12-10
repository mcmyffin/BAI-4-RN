package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomListEndPacket extends AbstractServerPacket {
    

    public ChatroomListEndPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        
    }

    @Override
    public void process() {
        getClientData().getMainController().processChatroomOverviewEnd();
    }
}
