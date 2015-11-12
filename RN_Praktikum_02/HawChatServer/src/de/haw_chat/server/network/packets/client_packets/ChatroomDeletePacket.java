package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomNotFoundExeption;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.ChatroomDeleteResponsePacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomDeletePacket extends AbstractClientPacket {
    private String chatroomName;

    public ChatroomDeletePacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        this.chatroomName = messageString.split(" ")[1];
    }

    @Override
    public void process() {

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
