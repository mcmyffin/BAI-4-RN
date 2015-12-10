package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.client.network.packets.client_packets.LoginPacket;
import de.haw_chat.client.network.packets.client_packets.LogoutPacket;
import de.haw_chat.client.network.packets.client_packets.RequestChatroomListPacket;
import de.haw_chat.common.operation.implementations.Status;

import javax.swing.*;

import java.io.IOException;
import java.util.Objects;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UserLoggedOutPacket extends AbstractServerPacket {
    private String username;

    public UserLoggedOutPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.username = messageString.split(" ")[1];
    }

    @Override
    public void process() {
        if (LogoutPacket.requestedLogout && Objects.equals(getClientData().getUsername(), username)) {
            if (username != null) {
                getClientData().setUsername(null);
                getClientData().getMainController().getFrame().setLoggedIn(false);
                getClientData().getMainController().getFrame().buttonLogin.setText("Anmelden");
                LogoutPacket.requestedLogout = false;
            }
        } else {
            try {
                getClientData().getMainController().getChatClient().getChatServerThread().writeToServer(new RequestChatroomListPacket());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
