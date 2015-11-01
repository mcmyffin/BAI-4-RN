package de.haw_chat.client.network.implementations;

import de.haw_chat.client.network.interfaces.ChatServerData;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatServerDataImpl implements ChatServerData {
    private String username;

    private ChatServerDataImpl() {
    }

    public static ChatServerData create() {
        return new ChatServerDataImpl();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
}
