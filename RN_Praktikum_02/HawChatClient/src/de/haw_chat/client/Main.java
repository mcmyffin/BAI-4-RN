package de.haw_chat.client;

import de.haw_chat.client.network.implementations.ChatDeviceFactory;
import de.haw_chat.client.network.interfaces.ChatClient;
import de.haw_chat.client.network.interfaces.ChatServerConfiguration;

/**
 * Created by Andreas on 31.10.2015.
 */
public class Main {
    public static void main(String[] args) {
        ChatServerConfiguration configuration =
                ChatDeviceFactory.createChatServerConfiguration(12345, 10, false);
        ChatClient chatServer = ChatDeviceFactory.createChatServer(configuration);

        Thread thread = new Thread(chatServer);
        thread.start();

        System.out.println("SERVER STARTED!");
    }
}
