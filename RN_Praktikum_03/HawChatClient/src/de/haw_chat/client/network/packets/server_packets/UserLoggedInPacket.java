package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import javax.swing.*;

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
        if (statusCode == OK) {
            getClientData().setUsername(username);
            getClientData().getMainController().getFrame().setLoggedIn(true);
            getClientData().getMainController().getFrame().buttonLogin.setText("Abmelden");
            getClientData().getMainController().getFrame().gotoChatroomOverview();
        } else if (statusCode == CLIENT_ALREADY_LOGGED_IN) {
            JOptionPane.showMessageDialog(null,
                    "Du bist bereits als " + getClientData().getUsername() + " eingeloggt!",
                    "Fehler!", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (statusCode == USERNAME_ALREADY_LOGGED_IN) {
            JOptionPane.showMessageDialog(null,
                    "Der Benutzername " + username + " ist bereits eingeloggt!",
                    "Fehler!", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (statusCode == PASSWORD_WRONG) {
            JOptionPane.showMessageDialog(null,
                    "Falsches Password!",
                    "Fehler!", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}
