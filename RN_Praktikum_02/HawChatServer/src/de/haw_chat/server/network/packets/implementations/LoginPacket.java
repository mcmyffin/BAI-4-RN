package de.haw_chat.server.network.packets.implementations;

import de.haw_chat.server.network.interfaces.ChatClientThread;
import de.haw_chat.server.network.packets.interfaces.ClientPacket;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LoginPacket implements ClientPacket {
    private ChatClientThread chatClientThread;
    private String username;

    public LoginPacket(ChatClientThread chatClientThread, String messageString) {
        this.chatClientThread = chatClientThread;
        this.username = messageString.split(" ")[0];
    }

    @Override
    public void process() {
        chatClientThread.getData().setUsername(username);
    }
}
