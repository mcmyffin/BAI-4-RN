package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomLeaveResponsePacket extends AbstractServerPacket {
    private Status statusCode;

    public ChatroomLeaveResponsePacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.statusCode = Status.getStatus(Integer.parseInt(messageString.split(" ")[1]));
    }

    @Override
    public void process() {
        getClientData().getMainController().leaveChatroom();
    }
}
