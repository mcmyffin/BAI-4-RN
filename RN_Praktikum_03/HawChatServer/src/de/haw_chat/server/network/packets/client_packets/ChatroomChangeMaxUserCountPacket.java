package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomNotFoundExeption;
import de.haw_chat.server.network.Exceptions.IllegalArgumentPacketException;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.AbstractServerPacket;
import de.haw_chat.server.network.packets.server_packets.ChatroomChangeMaxUserCountResponsePacket;
import de.haw_chat.server.network.packets.server_packets.LoginResponsePacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomChangeMaxUserCountPacket extends AbstractClientPacket{
    private String chatroomName;
    private int maxUserCount;

    public ChatroomChangeMaxUserCountPacket(ClientThread clientThread, String messageString) throws IllegalArgumentPacketException {
        super(clientThread);

        String[] splitedMessageString = messageString.split(" ");
        if(splitedMessageString.length != 3) throw new IllegalArgumentPacketException(messageString);

        this.chatroomName = messageString.split(" ")[1];
        this.maxUserCount = Integer.parseInt(messageString.split(" ")[2]);
    }

    @Override
    public void process() {

        if(!getClientData().isLoggedIn()){
            ChatroomChangeMaxUserCountResponsePacket packet = new ChatroomChangeMaxUserCountResponsePacket(Status.CLIENT_NOT_LOGGED_IN);
            getClientThread().writeToClient(packet);
            return;
        }

        try {
            Chatroom chat = getServerData().getChatroomByName(chatroomName);
            chat.changeMaxUserCount(maxUserCount,getClientThread());

        } catch (ChatroomNotFoundExeption chatroomNotFoundExeption) {

            Status response = Status.CHATROOM_NOT_FOUND;
            ChatroomChangeMaxUserCountResponsePacket packet = new ChatroomChangeMaxUserCountResponsePacket(response);
            getClientThread().writeToClient(packet);
        }
    }
}
