package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.ChatroomListBeginPacket;
import de.haw_chat.server.network.packets.server_packets.ChatroomListElementPacket;
import de.haw_chat.server.network.packets.server_packets.ChatroomListEndPacket;
import de.haw_chat.server.network.packets.server_packets.LoginResponsePacket;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomsRefreshPacket extends AbstractClientPacket {
    

    public ChatroomsRefreshPacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        
    }

    @Override
    public void process() {

        Collection<Chatroom> chatrooms = getServerData().getChatrooms();
        ClientThread client = getClientThread();

        ChatroomListBeginPacket beginPacket = new ChatroomListBeginPacket(chatrooms.size());
        ChatroomListEndPacket   endPacket   = new ChatroomListEndPacket();

        client.writeToClient(beginPacket);
        for(Chatroom chat : chatrooms){
            String chatroomName = chat.getName();
            int currentUser     = chat.getNumberOfConnectedUser();
            int maxUser         = chat.getMaxUser();

            ChatroomListElementPacket packet = new ChatroomListElementPacket(chatroomName,currentUser,maxUser);
            client.writeToClient(packet);
        }
        client.writeToClient(endPacket);
    }
}
