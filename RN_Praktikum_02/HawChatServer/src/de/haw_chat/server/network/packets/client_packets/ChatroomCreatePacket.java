package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.IllegalArgumentPacketException;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.ChatroomCreateResponsePacket;
import de.haw_chat.server.network.packets.server_packets.LoginResponsePacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomCreatePacket extends AbstractClientPacket {
    private String  chatroomName;
    private int     maxUserCount;
    private String  chatroomPassword;

    public ChatroomCreatePacket(ClientThread clientThread, String messageString) throws IllegalArgumentPacketException {
        super(clientThread);

        String[] splitedMessageString = messageString.split(" ");
        if(splitedMessageString.length < 3 || splitedMessageString.length > 4) throw new IllegalArgumentPacketException(messageString);

        this.chatroomName = splitedMessageString[1];
        this.maxUserCount = Integer.parseInt(splitedMessageString[2]);
        this.chatroomPassword = (splitedMessageString.length < 4? "" : splitedMessageString[3]);
    }

    @Override
    public void process() {

        if(!getClientData().isLoggedIn()){
            ChatroomCreateResponsePacket packet = new ChatroomCreateResponsePacket(Status.CLIENT_NOT_LOGGED_IN);
            getClientThread().writeToClient(packet);

        }else{
            Chatroom.create(getClientThread(),chatroomName,chatroomPassword,maxUserCount);
        }
    }
}
