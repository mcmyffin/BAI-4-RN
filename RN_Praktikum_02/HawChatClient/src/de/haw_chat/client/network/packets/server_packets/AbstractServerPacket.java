package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatClientData;
import de.haw_chat.client.network.interfaces.ChatServerData;
import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.client.network.packets.client_packets.AbstractClientPacket;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 31.10.2015.
 */
public abstract class AbstractServerPacket {
    private final ChatServerThread chatServerThread;

    protected AbstractServerPacket(ChatServerThread chatServerThread) {
        checkNotNull(chatServerThread);
        this.chatServerThread = chatServerThread;
    }

    protected final ChatServerData getClientData() {
        checkNotNull(chatServerThread.getData());
        return chatServerThread.getData();
    }

    protected final ChatClientData getServerData() {
        checkNotNull(chatServerThread.getChatClient());
        checkNotNull(chatServerThread.getChatClient().getData());
        return chatServerThread.getChatClient().getData();
    }

    protected final void sendToClient(AbstractClientPacket serverPacket) {
        try {
            chatServerThread.writeToClient(serverPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void process();
}
