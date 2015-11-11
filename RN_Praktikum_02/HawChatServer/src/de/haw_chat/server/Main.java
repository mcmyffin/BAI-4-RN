package de.haw_chat.server;

import de.haw_chat.server.network.implementations.DeviceFactory;
import de.haw_chat.server.network.interfaces.Server;
import de.haw_chat.server.network.interfaces.ServerConfiguration;

/**
 * Created by Andreas on 31.10.2015.
 */
public class Main {
    public static void main(String[] args) {
        ServerConfiguration configuration =
                DeviceFactory.createChatServerConfiguration(12345, 10, false);
        Server server = DeviceFactory.createChatServer(configuration);

        Thread thread = new Thread(server);
        thread.start();

        System.out.println("SERVER STARTED!");
    }
}
