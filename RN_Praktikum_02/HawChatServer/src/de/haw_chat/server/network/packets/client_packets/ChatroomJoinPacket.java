package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomNotFoundExeption;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.ChatroomJoinResponsePacket;
import de.haw_chat.server.network.packets.server_packets.LoginResponsePacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomJoinPacket extends AbstractClientPacket {
    private String chatroomName;
    private String chatroomPassword;

    public ChatroomJoinPacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        String[] splitedMessageString = messageString.split(" ");
        this.chatroomName = splitedMessageString[1];
        this.chatroomPassword = (splitedMessageString.length < 3? "": splitedMessageString[2]);
    }

    @Override
    public void process() {

        // if Client not Logged in
        if(!getClientData().isLoggedIn()){
            ChatroomJoinResponsePacket packet = new ChatroomJoinResponsePacket(Status.CLIENT_NOT_LOGGED_IN,chatroomName);
            getClientThread().writeToClient(packet);
            return;
        }

        try {
            Chatroom chat = getServerData().getChatroomByName(chatroomName);
            chat.join(getClientThread(),chatroomPassword);

        } catch (ChatroomNotFoundExeption chatroomNotFoundExeption) {

            Status response = Status.CHATROOM_NOT_FOUND;
            ChatroomJoinResponsePacket packet = new ChatroomJoinResponsePacket(response,chatroomName);
            getClientThread().writeToClient(packet);
        }
    }
}
