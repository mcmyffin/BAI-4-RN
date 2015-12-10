package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;

import java.util.regex.Pattern;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ClientNotLoggedInPacket extends AbstractServerPacket {
    private String messageString;

    public ClientNotLoggedInPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.messageString = messageString.replaceFirst(Pattern.quote("104 "), "");
    }

    @Override
    public void process() {
        System.err.println("Ung�ltiges Paket: " + messageString);
    }
}
