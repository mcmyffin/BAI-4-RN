package de.haw_chat.server.network.interfaces;

import de.haw_chat.server.network.packets.AbstractServerPacket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface ChatClientThread extends Runnable {
    int getClientId();
    Socket getClientSocket();
    ChatServer getChatServer();
    boolean isWorkerServiceRequested();
    void setWorkerServiceRequested(boolean workerServiceRequested);

    ChatClientData getData();
    void writeToClient(AbstractServerPacket serverPacket) throws IOException;
}
