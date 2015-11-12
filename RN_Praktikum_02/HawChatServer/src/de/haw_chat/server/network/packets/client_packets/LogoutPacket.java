package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomNotFoundExeption;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.LogoutResponsePacket;

import java.util.Set;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LogoutPacket extends AbstractClientPacket {
    

    public LogoutPacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        
    }

    @Override
    public void process() {

        // disconnect from connected Chats
        Set<String> connectedChatrooms = getClientData().getConnectedChats();
        for(String chatname : connectedChatrooms){
            try {
                Chatroom c = getServerData().getChatroomByName(chatname);
                c.leave(getClientThread());

            } catch (ChatroomNotFoundExeption chatroomNotFoundExeption) {
                // chatroom not found
            }
        }

        Status response = Status.OK;
        boolean isDisconnected = getServerData().removeUserClient(getClientThread());
        if(!isDisconnected) response = Status.CLIENT_NOT_LOGGED_IN;;

        LogoutResponsePacket packet = new LogoutResponsePacket(response);
        getClientThread().writeToClient(packet);
    }
}
