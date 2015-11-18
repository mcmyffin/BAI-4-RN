package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class MessageSendedPacket extends AbstractServerPacket {
    private String chatroom;
    private String username;
    private String message;
    private long timestamp;

    public MessageSendedPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.chatroom = messageString.split(" ")[1];
        this.timestamp = Long.parseLong(messageString.split(" ")[2]);
        this.username = messageString.split(" ")[3];

        this.message = messageString.split(" ")[4];
    }

    @Override
    public void process() {
        getClientData().getMainController().receiveMessage(chatroom, username, message);
    }
}
