package de.haw_chat.server.network.packets;

import de.haw_chat.server.network.interfaces.ChatClientThread;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UnknownClientPacket extends AbstractClientPacket {
    private String messageString;

    public UnknownClientPacket(ChatClientThread chatClientThread, String messageString) {
        super(chatClientThread);
        this.messageString = messageString;
    }

    @Override
    public void process() {
        System.err.println("[WARNING] received unknown client packet! request '" + messageString + "' ignored!");
    }
}
