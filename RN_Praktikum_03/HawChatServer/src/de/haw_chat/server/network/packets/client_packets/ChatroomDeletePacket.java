package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomNotFoundExeption;
import de.haw_chat.server.network.Exceptions.IllegalArgumentPacketException;
import de.haw_chat.server.network.implementations.ServerData;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.ChatroomDeleteResponsePacket;
import de.haw_chat.server.network.packets.server_packets.LoginResponsePacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomDeletePacket extends AbstractClientPacket {
    private String chatroomName;

    public ChatroomDeletePacket(ClientThread clientThread, String messageString) throws IllegalArgumentPacketException {
        super(clientThread);

        String[] splitedMessageString = messageString.split(" ");
        if(splitedMessageString.length != 2) throw new IllegalArgumentPacketException(messageString);

        this.chatroomName = messageString.split(" ")[1];
    }

    @Override
    public void process() {

        if(!getClientData().isLoggedIn()){
            ChatroomDeleteResponsePacket packet = new ChatroomDeleteResponsePacket(Status.CLIENT_NOT_LOGGED_IN);
            getClientThread().writeToClient(packet);
            return;
        }

        if (chatroomName.equals(ServerData.defaultChatName)) {
            Status response = Status.CHATROOM_PERMISSION_DENIED;
            ChatroomDeleteResponsePacket packet = new ChatroomDeleteResponsePacket(response);
            getClientThread().writeToClient(packet);
        }

        try {
            Chatroom chat = getServerData().getChatroomByName(chatroomName);
            chat.remove(getClientThread());

        } catch (ChatroomNotFoundExeption chatroomNotFoundExeption) {
            Status response = Status.CHATROOM_NOT_FOUND;
            ChatroomDeleteResponsePacket packet = new ChatroomDeleteResponsePacket(response);
            getClientThread().writeToClient(packet);
        }
    }
}
