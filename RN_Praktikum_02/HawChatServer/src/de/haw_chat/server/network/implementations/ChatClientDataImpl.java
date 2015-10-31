package de.haw_chat.server.network.implementations;

import de.haw_chat.server.network.interfaces.ChatClientData;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatClientDataImpl implements ChatClientData {
    private String username;

    private ChatClientDataImpl() {
    }

    public static ChatClientData create() {
        return new ChatClientDataImpl();
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
