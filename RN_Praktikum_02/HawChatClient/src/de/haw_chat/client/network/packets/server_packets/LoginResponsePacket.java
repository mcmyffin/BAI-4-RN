package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LoginResponsePacket extends AbstractServerPacket {
    private Status statusCode;

    public LoginResponsePacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.statusCode = Status.getStatus(Integer.parseInt(messageString.split(" ")[1]));
    }

    @Override
    public void process() {
        if (statusCode == OK) {
            getClientData().setUsername("Test");
            System.out.println("Successfully logged in!");
        } else if (statusCode == CLIENT_ALREADY_LOGGED_IN) {
            System.out.println("You are already logged in!");
        } else if (statusCode == USERNAME_ALREADY_LOGGED_IN) {
            System.out.println("Username already logged in!");
        } else if (statusCode == PASSWORD_WRONG) {
            System.out.println("Wrong username or password!");
        }
    }
}
