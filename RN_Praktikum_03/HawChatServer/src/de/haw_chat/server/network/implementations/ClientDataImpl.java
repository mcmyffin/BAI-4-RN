package de.haw_chat.server.network.implementations;

import de.haw_chat.server.network.interfaces.ClientData;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ClientDataImpl implements ClientData {

    private String username;
    private Set<String> connectedChatNames;

    private ClientDataImpl() {
        this.connectedChatNames = new HashSet();
    }

    public static ClientData create() {
        return new ClientDataImpl();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isLoggedIn() {
        return getUsername() != null;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Set<String> getConnectedChats() {
        return Collections.unmodifiableSet(this.connectedChatNames);
    }

    @Override
    public void addConnectedChats(String chatname) {
        checkNotNull(chatname);
        this.connectedChatNames.add(chatname);
    }

    @Override
    public void disconnectFromChat(String chatname) {
        checkNotNull(chatname);
        this.connectedChatNames.remove(chatname);
    }
}
