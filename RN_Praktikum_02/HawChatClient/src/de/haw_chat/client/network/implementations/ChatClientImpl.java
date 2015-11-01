package de.haw_chat.client.network.implementations;

import de.haw_chat.client.network.interfaces.ChatClientData;
import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.client.network.interfaces.ChatClient;
import de.haw_chat.client.network.interfaces.ChatServerConfiguration;

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
    private Semaphore clientThreadsSemaphore;

    private ChatClientImpl(ChatServerConfiguration configuration) {
        checkNotNull(configuration);
        this.configuration = configuration;
        this.chatClientData = null;
        this.clientThreadsSemaphore = null;
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
    public Semaphore getClientThreadsSemaphore() {
        return clientThreadsSemaphore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatClientImpl)) return false;

        ChatClientImpl that = (ChatClientImpl) o;

        if (getConfiguration() != null ? !getConfiguration().equals(that.getConfiguration()) : that.getConfiguration() != null)
            return false;
        if (chatClientData != null ? !chatClientData.equals(that.chatClientData) : that.chatClientData != null)
            return false;
        return !(getClientThreadsSemaphore() != null ? !getClientThreadsSemaphore().equals(that.getClientThreadsSemaphore()) : that.getClientThreadsSemaphore() != null);

    }

    @Override
    public int hashCode() {
        int result = getConfiguration() != null ? getConfiguration().hashCode() : 0;
        result = 31 * result + (chatClientData != null ? chatClientData.hashCode() : 0);
        result = 31 * result + (getClientThreadsSemaphore() != null ? getClientThreadsSemaphore().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatClient{" +
                "configuration=" + configuration +
                ", chatClientData=" + chatClientData +
                ", clientThreadsSemaphore=" + clientThreadsSemaphore +
                '}';
    }

    @Override
    public void run() {
        if (configuration.isSslEnabled()) {
            throw new UnsupportedOperationException("SSL support currently not implemented!");
        }

        final int serverPort = configuration.getServerPort();
        clientThreadsSemaphore = new Semaphore(configuration.getMaxThreads());
        chatClientData = ChatClientDataImpl.create();

        ServerSocket welcomeSocket;
        Socket connectionSocket;

        boolean serviceRequested = true;
        int nextThreadNumber = 0;

        try {
            welcomeSocket = new ServerSocket(serverPort);
            while (serviceRequested) {
                clientThreadsSemaphore.acquire();

                System.out.println("TCP Server is waiting for connection - listening TCP port " + serverPort);
                connectionSocket = welcomeSocket.accept();

                ChatServerThread chatServerThread =
                        ChatDeviceFactory.createChatClientThread(nextThreadNumber, connectionSocket, this);
                nextThreadNumber++;

                Thread thread = new Thread(chatServerThread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
