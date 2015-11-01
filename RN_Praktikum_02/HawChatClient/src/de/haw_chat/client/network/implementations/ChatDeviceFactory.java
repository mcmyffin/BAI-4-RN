package de.haw_chat.client.network.implementations;

import de.haw_chat.client.network.interfaces.*;

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

    public static ChatClient createChatServer(ChatServerConfiguration configuration) {
        return ChatClientImpl.create(configuration);
    }

    static ChatServerThread createChatClientThread(int clientId, Socket clientSocket, ChatClient chatClient) {
        return ChatServerThreadImpl.create(clientId, clientSocket, chatClient);
    }

    static ChatClientData createChatServerData() {
        return ChatClientDataImpl.create();
    }

    static ChatServerData createChatClientData() {
        return ChatServerDataImpl.create();
    }
}
