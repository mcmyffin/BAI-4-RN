package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomNotFoundExeption;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.MessageSendResponsePacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class MessageSendPacket extends AbstractClientPacket {
    private String chatroomName;
    private String message;

    public MessageSendPacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        this.chatroomName = messageString.split(" ")[1];
        this.message = messageString.split(" ")[2];
    }

    @Override
    public void process() {

        try {
            Chatroom chat = getServerData().getChatroomByName(chatroomName);
            chat.recieveMessageFrom(getClientThread(),message);

        } catch (ChatroomNotFoundExeption chatroomNotFoundExeption) {

            MessageSendResponsePacket packet = new MessageSendResponsePacket(Status.CHATROOM_NOT_FOUND);
            getClientThread().writeToClient(packet);
        }
    }
}
