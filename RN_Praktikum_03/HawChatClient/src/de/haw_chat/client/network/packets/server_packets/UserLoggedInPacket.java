package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.client.network.packets.client_packets.LoginPacket;
import de.haw_chat.client.network.packets.client_packets.RequestChatroomListPacket;
import de.haw_chat.common.operation.implementations.Status;

import javax.swing.*;

import java.io.IOException;
import java.util.Objects;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UserLoggedInPacket extends AbstractServerPacket {
    private String username;

    public UserLoggedInPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.username = messageString.split(" ")[1];
    }

    @Override
    public void process() {
        if (Objects.equals(LoginPacket.requestedUsername, username)) {
            if (username != null) {
                getClientData().setUsername(username);
                getClientData().getMainController().getFrame().setLoggedIn(true);
                getClientData().getMainController().getFrame().buttonLogin.setText("Abmelden");
                getClientData().getMainController().getFrame().gotoChatroomOverview();
                LoginPacket.requestedUsername = null;
                
                try {
                    getClientData().getMainController().getChatClient().getChatServerThread().writeToServer(new RequestChatroomListPacket());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
