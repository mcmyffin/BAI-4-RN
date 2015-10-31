package de.haw_chat.server.network.packets;

import de.haw_chat.server.network.interfaces.ChatClientThread;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LoginPacket extends AbstractClientPacket {
    private String username;

    public LoginPacket(ChatClientThread chatClientThread, String messageString) {
        super(chatClientThread);
        this.username = messageString.split(" ")[0];
    }

    @Override
    public void process() {
        if (getServerData().getTakenUsernames().contains(username)) {
            // Username is taken
            LoginResponsePacket response = new LoginResponsePacket(101);
            sendToClient(response);
            return;
        }

        // Old way to access client data (@Dimitri: remove line if recognized)
        chatClientThread.getData().setUsername(username);

        // The new (and better) way to access client data
        getServerData().getTakenUsernames().add(username);
        getClientData().setUsername(username);

        LoginResponsePacket response = new LoginResponsePacket(100);
        sendToClient(response);
    }
}
