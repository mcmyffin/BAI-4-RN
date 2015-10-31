package de.haw_chat.server.network.implementations;

import de.haw_chat.common.operation.interfaces.OperationData;
import de.haw_chat.server.network.interfaces.ChatClientData;
import de.haw_chat.server.network.interfaces.ChatClientThread;
import de.haw_chat.server.network.interfaces.ChatServer;
import de.haw_chat.server.network.packets.interfaces.ClientPacket;
import de.haw_chat.server.network.packets.interfaces.ServerPacket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import static com.google.common.base.Preconditions.checkNotNull;
import static de.haw_chat.common.operation.implementations.OperationDataManager.getOperationData;
import static de.haw_chat.server.network.implementations.ChatDeviceFactory.createChatClientData;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatClientThreadImpl implements ChatClientThread {
    private final int clientId;
    private final Socket clientSocket;
    private final ChatServer chatServer;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private boolean workerServiceRequested = true;
    private ChatClientData chatClientData;

    private ChatClientThreadImpl(int clientId, Socket clientSocket, ChatServer chatServer) {
        checkNotNull(clientSocket);
        checkNotNull(chatServer);
        this.clientId = clientId;
        this.clientSocket = clientSocket;
        this.chatServer = chatServer;
        this.inFromClient = null;
        this.outToClient = null;
        this.workerServiceRequested = true;
        this.chatClientData = createChatClientData();
    }

    public static ChatClientThread create(int clientId, Socket clientSocket, ChatServer chatServer) {
        return new ChatClientThreadImpl(clientId, clientSocket, chatServer);
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    public ChatServer getChatServer() {
        return chatServer;
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
    public ChatClientData getData() {
        return chatClientData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatClientThreadImpl)) return false;

        ChatClientThreadImpl that = (ChatClientThreadImpl) o;

        return getClientId() == that.getClientId();

    }

    @Override
    public int hashCode() {
        return getClientId();
    }

    @Override
    public String toString() {
        return "ChatClientThread{" +
                "clientId=" + clientId +
                ", clientSocket=" + clientSocket +
                ", chatServer=" + chatServer +
                ", workerServiceRequested=" + workerServiceRequested +
                ", chatClientData=" + chatClientData +
                '}';
    }

    private ClientPacket readFromClient() throws IOException {
        String request = inFromClient.readLine();
        System.out.println("TCP Worker Thread " + clientId + " detected job: " + request);

        final String PACKET_CLASS_PREFIX = "de.haw_chat.server.network.packets.implementations.";
        final String PACKET_CLASS_POSTFIX = "Packet";

        int operationCode = Integer.parseInt(request.split(" ")[0]);
        OperationData operationData = getOperationData(operationCode);
        if (!operationData.isClientOperation()) {
            System.err.println("[WARNING] client tried to send server packet! request '" + request + "' ignored!");
            return null;
        }

        String operationName = operationData.getOperationName();
        String className = PACKET_CLASS_PREFIX + operationName + PACKET_CLASS_POSTFIX;

        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor(ChatClientThread.class, String.class);
            ClientPacket object = (ClientPacket) ctor.newInstance(this, request);
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

        System.err.println("[WARNING] error while creating client packet! request '" + request + "' ignored!");
        return null;
    }

    @Override
    public void writeToClient(ServerPacket serverPacket) throws IOException {
        checkNotNull(serverPacket, "serverPacket is null!");
        String reply = serverPacket.toMessageString();
        outToClient.writeBytes(reply + '\r' + '\n');
        System.out.println("TCP Worker Thread " + clientId + " has written the message: " + reply);
    }

    @Override
    public void run() {
        try {
            inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToClient = new DataOutputStream(clientSocket.getOutputStream());

            while (workerServiceRequested) {
                ClientPacket clientPacket = readFromClient();
                if (clientPacket != null)
                    clientPacket.process();
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            chatServer.getClientThreadsSemaphore().release();
        }
    }
}
