package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ChatClientThread;
import de.haw_chat.server.network.packets.server_packets.LoginResponsePacket;

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
        System.out.println(username);
        System.out.println(password);
    }

    @Override
    public void process() {
        // TODO: Implement password check!
        
        if (getServerData().getTakenUsernames().contains(username)) {
            // Username is taken
            LoginResponsePacket response = new LoginResponsePacket(101);
            sendToClient(response);
            return;
        }

        // Set username and add it to taken usernames
        getServerData().getTakenUsernames().add(username);
        getClientData().setUsername(username);

        // Username successfully set
        LoginResponsePacket response = new LoginResponsePacket(100);
        sendToClient(response);
    }
}
