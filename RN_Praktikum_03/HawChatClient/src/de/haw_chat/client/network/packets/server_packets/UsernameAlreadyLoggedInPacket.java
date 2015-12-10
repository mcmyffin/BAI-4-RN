package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.client.network.packets.client_packets.LoginPacket;

import javax.swing.*;
import java.util.regex.Pattern;

import static de.haw_chat.common.operation.implementations.Status.OK;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UsernameAlreadyLoggedInPacket extends AbstractServerPacket {
    private String messageString;

    public UsernameAlreadyLoggedInPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.messageString = messageString.replaceFirst(Pattern.quote("104 "), "");
    }

    @Override
    public void process() {
        JOptionPane.showMessageDialog(null,
                "Der Benutzername " + LoginPacket.requestedUsername + " ist bereits eingeloggt!",
                "Fehler!", JOptionPane.ERROR_MESSAGE);
        return;
    }
}
