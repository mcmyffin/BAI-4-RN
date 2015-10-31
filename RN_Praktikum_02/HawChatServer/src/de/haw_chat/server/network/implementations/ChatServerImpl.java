package de.haw_chat.server.network.implementations;

import de.haw_chat.server.network.interfaces.ChatClientThread;
import de.haw_chat.server.network.interfaces.ChatServer;
import de.haw_chat.server.network.interfaces.ChatServerConfiguration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatServerImpl implements ChatServer {
    private final ChatServerConfiguration configuration;
    private Semaphore clientThreadsSemaphore;

    private ChatServerImpl(ChatServerConfiguration configuration) {
        checkNotNull(configuration);
        this.configuration = configuration;
        this.clientThreadsSemaphore = null;
    }

    public static ChatServer create(ChatServerConfiguration configuration) {
        return new ChatServerImpl(configuration);
    }

    @Override
    public ChatServerConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public Semaphore getClientThreadsSemaphore() {
        return clientThreadsSemaphore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatServerImpl)) return false;

        ChatServerImpl that = (ChatServerImpl) o;

        return getConfiguration().equals(that.getConfiguration());

    }

    @Override
    public int hashCode() {
        return getConfiguration().hashCode();
    }

    @Override
    public String toString() {
        return "ChatServer{" +
                "configuration=" + configuration +
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

                ChatClientThread chatClientThread =
                        ChatDeviceFactory.createChatClientThread(nextThreadNumber, connectionSocket, this);
                nextThreadNumber++;

                Thread thread = new Thread(chatClientThread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
