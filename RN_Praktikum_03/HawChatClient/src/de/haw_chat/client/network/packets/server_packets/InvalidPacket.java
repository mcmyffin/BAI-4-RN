package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import java.util.regex.Pattern;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class InvalidPacket extends AbstractServerPacket {
    private String messageString;

    public InvalidPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.messageString = messageString.replaceFirst(Pattern.quote("104 "), "");
    }

    @Override
    public void process() {
        System.err.println("Ungültiges Paket: " + messageString);
    }
}
