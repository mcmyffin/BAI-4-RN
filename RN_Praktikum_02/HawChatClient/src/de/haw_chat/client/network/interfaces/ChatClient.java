package de.haw_chat.client.network.interfaces;

import de.haw_chat.client.network.packets.client_packets.AbstractClientPacket;

import java.io.IOException;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface ChatClient extends Runnable {
    ChatServerConfiguration getConfiguration();
    ChatClientData getData();
    ChatServerThread getChatServerThread();
}
