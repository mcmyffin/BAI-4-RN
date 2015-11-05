package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ChatClientThread;
import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.network.packets.server_packets.LoginResponsePacket;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LoginPacket extends AbstractClientPacket {
    private String username;
    private String password;

    public LoginPacket(ChatClientThread chatClientThread, String messageString) {
        super(chatClientThread);
        this.username = messageString.split(" ")[1];
        this.password = messageString.split(" ")[2];
    }

    @Override
    public void process() {
        if (getClientData().getUsername() != null) {
            // Client already logged in with some username
            LoginResponsePacket response = new LoginResponsePacket(CLIENT_ALREADY_LOGGED_IN);
            sendToClient(response);
            return;
        }

        if (getServerData().getTakenUsernames().contains(username)) {
            // Username already logged in by other user
            LoginResponsePacket response = new LoginResponsePacket(USERNAME_ALREADY_LOGGED_IN);
            sendToClient(response);
            return;
        }

        // TODO: Implement password check!
        if (false) {
            // Wrong username or password
            LoginResponsePacket response = new LoginResponsePacket(PASSWORD_WRONG);
            sendToClient(response);
            return;
        }

        // Set username and add it to taken usernames
        getServerData().getTakenUsernames().add(username);
        getClientData().setUsername(username);

        // Username successfully set
        LoginResponsePacket response = new LoginResponsePacket(OK);
        sendToClient(response);
    }
}
