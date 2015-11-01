package de.haw_chat.client.network.implementations;

import de.haw_chat.client.network.interfaces.ChatClientData;
import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.client.network.interfaces.ChatClient;
import de.haw_chat.client.network.interfaces.ChatServerConfiguration;
import de.haw_chat.client.network.packets.client_packets.LoginPacket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatClientImpl implements ChatClient {
    private final ChatServerConfiguration configuration;
    private ChatClientData chatClientData;

    private ChatClientImpl(ChatServerConfiguration configuration) {
        checkNotNull(configuration);
        this.configuration = configuration;
        this.chatClientData = null;
    }

    public static ChatClient create(ChatServerConfiguration configuration) {
        return new ChatClientImpl(configuration);
    }

    @Override
    public ChatServerConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public ChatClientData getData() {
        return chatClientData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatClientImpl)) return false;

        ChatClientImpl that = (ChatClientImpl) o;

        if (getConfiguration() != null ? !getConfiguration().equals(that.getConfiguration()) : that.getConfiguration() != null)
            return false;
        return !(chatClientData != null ? !chatClientData.equals(that.chatClientData) : that.chatClientData != null);

    }

    @Override
    public int hashCode() {
        int result = getConfiguration() != null ? getConfiguration().hashCode() : 0;
        result = 31 * result + (chatClientData != null ? chatClientData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatClient{" +
                "configuration=" + configuration +
                ", chatClientData=" + chatClientData +
                '}';
    }

    @Override
    public void run() {
        if (configuration.isSslEnabled()) {
            throw new UnsupportedOperationException("SSL support currently not implemented!");
        }

        final String serverHost = configuration.getServerHost();
        final int serverPort = configuration.getServerPort();
        chatClientData = ChatClientDataImpl.create();
        int serverId = 0;

        Socket connectionSocket;

        try {
            connectionSocket = new Socket(serverHost, serverPort);
            System.out.println("TCP Client is connected - TCP host " + serverHost + ", TCP port " + serverPort);

            ChatServerThread chatServerThread =
                    ChatDeviceFactory.createChatServerThread(serverId, connectionSocket, this);

            Thread thread = new Thread(chatServerThread);
            thread.start();

            while (!chatServerThread.isInitialized()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // TODO: Remove (it's just a test)
            chatServerThread.writeToServer(new LoginPacket("Hans", "Wurst"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
