package de.haw_chat.server.network.implementations;

import de.haw_chat.server.network.interfaces.ChatClientData;
import de.haw_chat.server.network.interfaces.ChatClientThread;
import de.haw_chat.server.network.interfaces.ChatServer;
import de.haw_chat.server.network.interfaces.ChatServerConfiguration;

import java.net.Socket;
import java.util.Properties;

/**
 * Created by Andreas on 31.10.2015.
 */
public final class ChatDeviceFactory {
    public static ChatServerConfiguration createChatServerConfiguration(int serverPort, int maxThreads, boolean sslEnabled) {
        return ChatServerConfigurationImpl.create(serverPort, maxThreads, sslEnabled);
    }

    public static ChatServerConfiguration createChatServerConfiguration(Properties properties) {
        return ChatServerConfigurationImpl.create(properties);
    }

    public static ChatServer createChatServer(ChatServerConfiguration configuration) {
        return ChatServerImpl.create(configuration);
    }

    static ChatClientThread createChatClientThread(int clientId, Socket clientSocket, ChatServer chatServer) {
        return ChatClientThreadImpl.create(clientId, clientSocket, chatServer);
    }

    static ChatClientData createChatClientData() {
        return ChatClientDataImpl.create();
    }
}
