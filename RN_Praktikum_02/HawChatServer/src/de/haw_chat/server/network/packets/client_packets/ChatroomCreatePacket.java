package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomAlreadyExistingExeption;
import de.haw_chat.server.network.Exceptions.InvalidMaxUserSizeException;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.ChatroomCreateResponsePacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomCreatePacket extends AbstractClientPacket {
    private String chatroomName;
    private int maxUserCount;
    private String chatroomPassword;

    public ChatroomCreatePacket(ClientThread clientThread, String messageString) {
        super(clientThread);
        this.chatroomName = messageString.split(" ")[1];
        this.maxUserCount = Integer.parseInt(messageString.split(" ")[2]);
        this.chatroomPassword = messageString.split(" ")[3];
    }

    @Override
    public void process() {
        try {
            Chatroom.create(getClientThread(),chatroomName,chatroomPassword,maxUserCount);
        } catch (InvalidMaxUserSizeException e) {

            Status response = Status.CHATROOM_MEMBER_COUNT_INVALID;
            ChatroomCreateResponsePacket packet = new ChatroomCreateResponsePacket(response);
            getClientThread().writeToClient(packet);

        } catch (ChatroomAlreadyExistingExeption chatroomAlreadyExistingExeption) {

            Status response = Status.CHATROOM_NAME_ALREADY_TAKEN;
            ChatroomCreateResponsePacket packet = new ChatroomCreateResponsePacket(response);
            getClientThread().writeToClient(packet);
        }
    }
}
