package de.haw_chat.server.network.interfaces;

import java.util.concurrent.Semaphore;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface ChatServer extends Runnable {
    ChatServerConfiguration getConfiguration();

    Semaphore getClientThreadsSemaphore();
}
