package de.haw_chat.server.network.interfaces;

import de.haw_chat.server.network.packets.server_packets.AbstractServerPacket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface ClientThread extends Runnable {
    int getClientId();
    Socket getClientSocket();
    Server getServer();
    boolean isWorkerServiceRequested();
    void setWorkerServiceRequested(boolean workerServiceRequested);

    ClientData getData();
    void writeToClient(AbstractServerPacket serverPacket);
}
