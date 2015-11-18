package de.haw_chat.server.network.packets.client_packets;

import com.google.common.base.Joiner;
import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomNotFoundExeption;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.LoginResponsePacket;
import de.haw_chat.server.network.packets.server_packets.MessageSendResponsePacket;

import java.util.Arrays;

/**
 * Created by Andreas on 31.10.2015.
 */
public class MessageSendPacket extends AbstractClientPacket {
    private String chatroomName;
    private String message;

    public MessageSendPacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        String[] splitedString = messageString.split(" ");
        this.chatroomName = messageString.split(" ")[1];

        splitedString = Arrays.copyOfRange(splitedString, 2, splitedString.length);
        message = Joiner.on(" ").join(splitedString);
    }

    @Override
    public void process() {

        if(!getClientData().isLoggedIn()){
            MessageSendResponsePacket packet = new MessageSendResponsePacket(Status.CLIENT_NOT_LOGGED_IN);
            getClientThread().writeToClient(packet);
            return;
        }

        try {
            Chatroom chat = getServerData().getChatroomByName(chatroomName);
            chat.recieveMessageFrom(getClientThread(),message);

        } catch (ChatroomNotFoundExeption chatroomNotFoundExeption) {

            MessageSendResponsePacket packet = new MessageSendResponsePacket(Status.CHATROOM_NOT_FOUND);
            getClientThread().writeToClient(packet);
        }
    }
}
