package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomNotFoundExeption;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.ChatroomListBeginPacket;
import de.haw_chat.server.network.packets.server_packets.ChatroomListElementPacket;
import de.haw_chat.server.network.packets.server_packets.ChatroomListEndPacket;
import de.haw_chat.server.network.packets.server_packets.MessageSendResponsePacket;

import java.util.Collection;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomRefreshPacket extends AbstractClientPacket {
    private String chatroomName;

    public ChatroomRefreshPacket(ClientThread clientThread, String messageString) {
        super(clientThread);

        this.chatroomName = messageString.split(" ")[1];
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
            chat.sendDistributedMemberList();
        } catch (ChatroomNotFoundExeption chatroomNotFoundExeption) {

            MessageSendResponsePacket packet = new MessageSendResponsePacket(Status.CHATROOM_NOT_FOUND);
            getClientThread().writeToClient(packet);
        }
    }
}
