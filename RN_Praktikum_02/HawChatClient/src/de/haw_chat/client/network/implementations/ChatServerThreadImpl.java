package de.haw_chat.client.network.implementations;

import de.haw_chat.client.network.interfaces.ChatClient;
import de.haw_chat.common.operation.interfaces.OperationData;
import de.haw_chat.client.network.interfaces.ChatServerData;
import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.client.network.packets.client_packets.AbstractClientPacket;
import de.haw_chat.client.network.packets.server_packets.AbstractServerPacket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import static com.google.common.base.Preconditions.checkNotNull;
import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;
import static de.haw_chat.client.network.implementations.ChatDeviceFactory.createChatServerData;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatServerThreadImpl implements ChatServerThread {
    private final int serverId;
    private final Socket serverSocket;
    private final ChatClient chatClient;
    private BufferedReader inFromServer;
    private DataOutputStream outToServer;
    private boolean workerServiceRequested = true;
    private ChatServerData chatServerData;
    private boolean initialized;

    private ChatServerThreadImpl(int serverId, Socket serverSocket, ChatClient chatClient) {
        checkNotNull(serverSocket);
        checkNotNull(chatClient);
        this.serverId = serverId;
        this.serverSocket = serverSocket;
        this.chatClient = chatClient;
        this.inFromServer = null;
        this.outToServer = null;
        this.workerServiceRequested = true;
        this.chatServerData = createChatServerData();
        this.initialized = false;
    }

    public static ChatServerThread create(int clientId, Socket clientSocket, ChatClient chatClient) {
        return new ChatServerThreadImpl(clientId, clientSocket, chatClient);
    }

    @Override
    public int getServerId() {
        return serverId;
    }

    @Override
    public Socket getServerSocket() {
        return serverSocket;
    }

    @Override
    public ChatClient getChatClient() {
        return chatClient;
    }

    @Override
    public boolean isWorkerServiceRequested() {
        return workerServiceRequested;
    }

    @Override
    public void setWorkerServiceRequested(boolean workerServiceRequested) {
        this.workerServiceRequested = workerServiceRequested;
    }

    @Override
    public ChatServerData getData() {
        return chatServerData;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatServerThreadImpl)) return false;

        ChatServerThreadImpl that = (ChatServerThreadImpl) o;

        return getServerId() == that.getServerId();

    }

    @Override
    public int hashCode() {
        return getServerId();
    }

    @Override
    public String toString() {
        return "ChatServerThread{" +
                "serverId=" + serverId +
                ", serverSocket=" + serverSocket +
                ", chatClient=" + chatClient +
                ", inFromServer=" + inFromServer +
                ", chatServerData=" + chatServerData +
                ", initialized=" + initialized +
                '}';
    }

    private AbstractServerPacket readFromServer() throws IOException {
        String response = inFromServer.readLine();
        System.out.println("TCP Server Thread " + serverId + " detected job: " + response);

        final String PACKET_CLASS_PREFIX = AbstractServerPacket.class.getPackage().getName() + ".";
        final String PACKET_CLASS_POSTFIX = "Packet";

        int operationCode = Integer.parseInt(response.split(" ")[0]);
        OperationData operationData = getOperationData(operationCode);
        if (operationData.isClientOperation()) {
            System.err.println("[WARNING] server tried to send client packet! response '" + response + "' ignored!");
            return null;
        }

        String operationName = operationData.getOperationName();
        String className = PACKET_CLASS_PREFIX + operationName + PACKET_CLASS_POSTFIX;

        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor(ChatServerThread.class, String.class);
            AbstractServerPacket object = (AbstractServerPacket) ctor.newInstance(this, response);
            return object;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.err.println("[WARNING] error while creating server packet! response '" + response + "' ignored!");
        return null;
    }

    @Override
    public void writeToServer(AbstractClientPacket clientPacket) throws IOException {
        checkNotNull(clientPacket, "clientPacket is null!");
        String request = clientPacket.toMessageString();
        outToServer.writeBytes(request + '\r' + '\n');
        System.out.println("TCP Server Thread " + serverId + " has written the message: " + request);
    }

    @Override
    public void run() {
        try {
            inFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            outToServer = new DataOutputStream(serverSocket.getOutputStream());

            initialized = true;

            while (workerServiceRequested) {
                AbstractServerPacket clientPacket = readFromServer();
                if (clientPacket != null)
                    clientPacket.process();
            }

            serverSocket.close();
        } catch (IOException e) {
            System.out.println("TCP Server " + serverId + " closed the connection");
        }
    }
}
