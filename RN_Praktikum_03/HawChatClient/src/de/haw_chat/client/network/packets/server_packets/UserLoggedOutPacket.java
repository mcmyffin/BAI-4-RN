package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.client.network.packets.client_packets.LoginPacket;
import de.haw_chat.client.network.packets.client_packets.LogoutPacket;
import de.haw_chat.common.operation.implementations.Status;

import javax.swing.*;

import java.util.Objects;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UserLoggedOutPacket extends AbstractServerPacket {
    private Status statusCode;
    private String username;

    public UserLoggedOutPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.statusCode = Status.getStatus(Integer.parseInt(messageString.split(" ")[1]));
        this.username = messageString.split(" ")[2];
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
        }
    }
}
