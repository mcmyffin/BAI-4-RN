package de.haw_chat.client.network.implementations;

import de.haw_chat.client.network.interfaces.ChatClientData;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatClientDataImpl implements ChatClientData {
    private Collection<String> takenUsernames;

    private ChatClientDataImpl() {
        this.takenUsernames = new HashSet<>();
    }

    public static ChatClientData create() {
        return new ChatClientDataImpl();
    }

    @Override
    public Collection<String> getTakenUsernames() {
        return takenUsernames;
    }
}
