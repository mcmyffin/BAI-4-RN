package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.StatusEnum;

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
        if (statusCode == StatusEnum.OK.getStatusCode()) {
            getClientData().setUsername("Test");
            System.out.println("Successfully logged in!");
        } else if (statusCode == StatusEnum.CLIENT_ALREADY_LOGGED_IN.getStatusCode()) {
            System.out.println("You are already logged in!");
        } else if (statusCode == StatusEnum.USERNAME_ALREADY_LOGGED_IN.getStatusCode()) {
            System.out.println("Username already logged in!");
        } else if (statusCode == StatusEnum.PASSWORD_WRONG.getStatusCode()) {
            System.out.println("Wrong username or password!");
        }
    }
}
