package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.Exceptions.IllegalArgumentPacketException;
import de.haw_chat.server.network.implementations.ServerData;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.LoginResponsePacket;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LoginPacket extends AbstractClientPacket {
    private String username;
    private String password;

    public LoginPacket(ClientThread clientThread, String messageString) throws IllegalArgumentPacketException {
        super(clientThread);

        String[] splitedMessageString = messageString.split(" ");
        if(splitedMessageString.length != 2 ) throw new IllegalArgumentPacketException(messageString);

        this.username = splitedMessageString[1];
        this.password = "";
    }

    @Override
    public void process() {
        if (getClientData().isLoggedIn()) {
            // Client already logged in
            LoginResponsePacket response = new LoginResponsePacket(CLIENT_ALREADY_LOGGED_IN,username);
            sendToClient(response);
            return;
        }

        if (getServerData().containsUser(username)) {
            // Username already logged in by another user
            LoginResponsePacket response = new LoginResponsePacket(USERNAME_ALREADY_LOGGED_IN,username);
            sendToClient(response);
            return;
        }

        // TODO: Implement password check!
        if (false) {
            // Wrong username or password
            LoginResponsePacket response = new LoginResponsePacket(PASSWORD_WRONG,username);
            sendToClient(response);
            return;
        }

        // Set username and add it to taken usernames
        getClientData().setUsername(username);
        getServerData().addUserClient(username, getClientThread());

        // Username successfully set
        LoginResponsePacket response = new LoginResponsePacket(OK,username);
        sendToClient(response);

        // add user to defaultChat
        getServerData().getDefaultChatRoom().join(getClientThread(),"");
    }
}
