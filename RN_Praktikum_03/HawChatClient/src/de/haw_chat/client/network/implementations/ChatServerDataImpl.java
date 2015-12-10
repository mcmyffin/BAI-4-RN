package de.haw_chat.client.network.implementations;

import de.haw_chat.client.network.interfaces.ChatServerData;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatServerDataImpl implements ChatServerData {
    private ChatServerDataImpl() {
    }

    public static ChatServerData create() {
        return new ChatServerDataImpl();
    }
}
