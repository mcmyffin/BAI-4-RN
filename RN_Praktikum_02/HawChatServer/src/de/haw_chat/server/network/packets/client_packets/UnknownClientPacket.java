package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomNotFoundExeption;
import de.haw_chat.server.network.Exceptions.IllegalArgumentPacketException;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.AbstractServerPacket;
import de.haw_chat.server.network.packets.server_packets.ChatroomChangeNameResponsePacket;
import de.haw_chat.server.network.packets.server_packets.UnknownPacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UnknownClientPacket extends AbstractClientPacket {
    private String message;

    public UnknownClientPacket(ClientThread clientThread, String messageString) throws IllegalArgumentPacketException {
        super(clientThread);
        this.message = messageString;
    }

    @Override
    public void process() {
        AbstractServerPacket packet = new UnknownPacket(this.message);
        getClientThread().writeToClient(packet);

    }
}
