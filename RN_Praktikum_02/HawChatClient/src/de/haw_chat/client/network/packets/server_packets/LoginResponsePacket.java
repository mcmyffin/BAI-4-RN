package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LoginResponsePacket extends AbstractServerPacket {
    private int statusCode;

    public LoginResponsePacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.statusCode = Integer.parseInt(messageString.split(" ")[1]);
    }

    @Override
    public void process() {
        if (statusCode == 100) {
            getClientData().setUsername("Test");
            System.out.println("Successfully logged in!");
        } else if (statusCode == 101) {
            System.out.println("You are already logged in!");
        } else if (statusCode == 102) {
            System.out.println("Username already logged in!");
        } else if (statusCode == 103) {
            System.out.println("Wrong username or password!");
        }
    }
}
