package de.haw_chat.server.network.packets.client_packets;

import de.haw_chat.server.network.interfaces.ChatClientData;
import de.haw_chat.server.network.interfaces.ChatClientThread;
import de.haw_chat.server.network.interfaces.ChatServerData;
import de.haw_chat.server.network.packets.server_packets.AbstractServerPacket;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 31.10.2015.
 */
public abstract class AbstractClientPacket {
    private final ChatClientThread chatClientThread;

    protected AbstractClientPacket(ChatClientThread chatClientThread) {
        checkNotNull(chatClientThread);
        this.chatClientThread = chatClientThread;
    }

    protected final ChatClientData getClientData() {
        checkNotNull(chatClientThread.getData());
        return chatClientThread.getData();
    }

    protected final ChatServerData getServerData() {
        checkNotNull(chatClientThread.getChatServer());
        checkNotNull(chatClientThread.getChatServer().getData());
        return chatClientThread.getChatServer().getData();
    }

    protected final void sendToClient(AbstractServerPacket serverPacket) {
        try {
            chatClientThread.writeToClient(serverPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void process();
}
