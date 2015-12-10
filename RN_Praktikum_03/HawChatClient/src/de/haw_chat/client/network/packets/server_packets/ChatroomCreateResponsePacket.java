package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import javax.swing.*;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomCreateResponsePacket extends AbstractServerPacket {
    private Status statusCode;

    public ChatroomCreateResponsePacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.statusCode = Status.getStatus(Integer.parseInt(messageString.split(" ")[1]));
    }

    @Override
    public void process() {
        if (statusCode != OK) {
            JOptionPane.showMessageDialog(null,
                    "Fehler-Code: " + statusCode.getStatusName() + "!",
                    "Fehler!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}
