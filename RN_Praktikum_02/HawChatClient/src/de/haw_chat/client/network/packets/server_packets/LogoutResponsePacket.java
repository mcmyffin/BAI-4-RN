package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class LogoutResponsePacket extends AbstractServerPacket {
    private Status statusCode;

    public LogoutResponsePacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.statusCode = Status.getStatus(Integer.parseInt(messageString.split(" ")[1]));
    }

    @Override
    public void process() {
        getClientData().setUsername(null);
        getClientData().getMainController().getFrame().setLoggedIn(false);
        getClientData().getMainController().getFrame().buttonLogin.setText("Anmelden");
    }
}
