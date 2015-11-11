package de.haw_chat.server.network.interfaces;

import de.haw_chat.server.network.implementations.ServerData;

import java.util.concurrent.Semaphore;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface Server extends Runnable {
    ServerConfiguration getConfiguration();
    ServerData getData();

    Semaphore getClientThreadsSemaphore();
}
