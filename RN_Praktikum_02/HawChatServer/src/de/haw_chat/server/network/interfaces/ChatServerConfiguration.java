package de.haw_chat.server.network.interfaces;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface ChatServerConfiguration {
    int getServerPort();
    int getMaxThreads();
    boolean isSslEnabled();
}
