package de.haw_chat.client.network.packets.server_packets;

import com.google.common.base.Joiner;
import de.haw_chat.client.Main;
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
    private String timestamp;

    public MessageSendedPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        //201 13:48:25 - dima: hallo
        String[] splitedMessageString = messageString.split(" ");
        this.chatroom   = Main.defaultChatroomName;
        this.timestamp  = splitedMessageString[1];
        this.username   = splitedMessageString[3].replace(":","");

        splitedMessageString= Arrays.copyOfRange(splitedMessageString, 5, splitedMessageString.length);
        message = Joiner.on(" ").join(splitedMessageString);
    }

    @Override
    public void process() {
        getClientData().getMainController().receiveMessage(chatroom, username, message, timestamp);
    }
}
