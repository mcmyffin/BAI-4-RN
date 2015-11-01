package de.haw_chat.client.network.interfaces;

import java.util.concurrent.Semaphore;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface ChatClient extends Runnable {
    ChatServerConfiguration getConfiguration();
    ChatClientData getData();
}
