package de.haw_chat.client.network.implementations;

import de.haw_chat.client.network.interfaces.*;

import java.net.Socket;
import java.util.Properties;

/**
 * Created by Andreas on 31.10.2015.
 */
public final class ChatDeviceFactory {
    public static ChatServerConfiguration createChatServerConfiguration(String serverHost, int serverPort, boolean sslEnabled) {
        return ChatServerConfigurationImpl.create(serverHost, serverPort, sslEnabled);
    }

    public static ChatServerConfiguration createChatServerConfiguration(Properties properties) {
        return ChatServerConfigurationImpl.create(properties);
    }

    public static ChatClient createChatClient(ChatServerConfiguration configuration) {
        return ChatClientImpl.create(configuration);
    }

    static ChatServerThread createChatServerThread(int serverId, Socket clientSocket, ChatClient chatClient) {
        return ChatServerThreadImpl.create(serverId, clientSocket, chatClient);
    }

    static ChatClientData createChatClientData() {
        return ChatClientDataImpl.create();
    }

    static ChatServerData createChatServerData() {
        return ChatServerDataImpl.create();
    }
}
