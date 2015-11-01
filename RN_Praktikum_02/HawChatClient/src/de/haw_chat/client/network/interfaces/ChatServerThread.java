package de.haw_chat.client.network.interfaces;

import de.haw_chat.client.network.packets.client_packets.AbstractClientPacket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface ChatServerThread extends Runnable {
    int getClientId();
    Socket getClientSocket();
    ChatClient getChatClient();
    boolean isWorkerServiceRequested();
    void setWorkerServiceRequested(boolean workerServiceRequested);

    ChatServerData getData();
    void writeToClient(AbstractClientPacket serverPacket) throws IOException;
}
