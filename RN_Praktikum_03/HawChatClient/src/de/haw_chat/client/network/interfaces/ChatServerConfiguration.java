package de.haw_chat.client.network.interfaces;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface ChatServerConfiguration {
    String getServerHost();
    int getServerPort();
    boolean isSslEnabled();
}
