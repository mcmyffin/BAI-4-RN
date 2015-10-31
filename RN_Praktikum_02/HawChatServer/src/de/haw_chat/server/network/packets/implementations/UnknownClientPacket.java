package de.haw_chat.server.network.packets.implementations;

import de.haw_chat.server.network.interfaces.ChatClientThread;
import de.haw_chat.server.network.packets.interfaces.ClientPacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UnknownClientPacket implements ClientPacket {
    private ChatClientThread chatClientThread;
    private String messageString;

    public UnknownClientPacket(ChatClientThread chatClientThread, String messageString) {
        this.chatClientThread = chatClientThread;
        this.messageString = messageString;
    }

    @Override
    public void process() {
        System.err.println("[WARNING] received unknown client packet! request '" + messageString + "' ignored!");
    }
}
