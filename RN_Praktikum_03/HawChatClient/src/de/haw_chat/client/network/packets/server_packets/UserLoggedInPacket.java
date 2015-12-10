package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.client.network.packets.client_packets.LoginPacket;
import de.haw_chat.common.operation.implementations.Status;

import javax.swing.*;

import java.util.Objects;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UserLoggedInPacket extends AbstractServerPacket {
    private Status statusCode;
    private String username;

    public UserLoggedInPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.statusCode = Status.getStatus(Integer.parseInt(messageString.split(" ")[1]));
        this.username = messageString.split(" ")[2];
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
            }
        }
    }
}
