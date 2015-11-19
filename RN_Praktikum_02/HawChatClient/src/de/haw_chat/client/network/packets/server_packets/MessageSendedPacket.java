package de.haw_chat.client.network.packets.server_packets;

import com.google.common.base.Joiner;
import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import java.util.Arrays;

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

        String[] splitedString = messageString.split(" ");
        splitedString = Arrays.copyOfRange(splitedString, 4, splitedString.length);
        message = Joiner.on(" ").join(splitedString);
    }

    @Override
    public void process() {
        getClientData().getMainController().receiveMessage(chatroom, username, message);
    }
}
