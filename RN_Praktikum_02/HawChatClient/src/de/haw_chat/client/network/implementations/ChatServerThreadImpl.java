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
import static de.haw_chat.client.network.implementations.ChatDeviceFactory.createChatClientData;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatServerThreadImpl implements ChatServerThread {
    private final int clientId;
    private final Socket clientSocket;
    private final ChatClient chatClient;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private boolean workerServiceRequested = true;
    private ChatServerData chatServerData;

    private ChatServerThreadImpl(int clientId, Socket clientSocket, ChatClient chatClient) {
        checkNotNull(clientSocket);
        checkNotNull(chatClient);
        this.clientId = clientId;
        this.clientSocket = clientSocket;
        this.chatClient = chatClient;
        this.inFromClient = null;
        this.outToClient = null;
        this.workerServiceRequested = true;
        this.chatServerData = createChatClientData();
    }

    public static ChatServerThread create(int clientId, Socket clientSocket, ChatClient chatClient) {
        return new ChatServerThreadImpl(clientId, clientSocket, chatClient);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatServerThreadImpl)) return false;

        ChatServerThreadImpl that = (ChatServerThreadImpl) o;

        return getClientId() == that.getClientId();

    }

    @Override
    public int hashCode() {
        return getClientId();
    }

    @Override
    public String toString() {
        return "ChatServerThread{" +
                "clientId=" + clientId +
                ", clientSocket=" + clientSocket +
                ", chatClient=" + chatClient +
                ", workerServiceRequested=" + workerServiceRequested +
                ", chatServerData=" + chatServerData +
                '}';
    }

    private AbstractServerPacket readFromClient() throws IOException {
        String request = inFromClient.readLine();
        System.out.println("TCP Worker Thread " + clientId + " detected job: " + request);

        final String PACKET_CLASS_PREFIX = AbstractServerPacket.class.getPackage().getName() + ".";
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
            Constructor<?> ctor = clazz.getConstructor(ChatServerThread.class, String.class);
            AbstractServerPacket object = (AbstractServerPacket) ctor.newInstance(this, request);
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
    public void writeToClient(AbstractClientPacket serverPacket) throws IOException {
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
                AbstractServerPacket clientPacket = readFromClient();
                if (clientPacket != null)
                    clientPacket.process();
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            chatClient.getClientThreadsSemaphore().release();
        }
    }
}
