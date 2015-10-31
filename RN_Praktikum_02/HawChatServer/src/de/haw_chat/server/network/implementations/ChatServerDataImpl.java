package de.haw_chat.server.network.implementations;

import de.haw_chat.server.network.interfaces.ChatServerData;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatServerDataImpl implements ChatServerData {
    private Collection<String> takenUsernames;

    private ChatServerDataImpl() {
        this.takenUsernames = new HashSet<>();
    }

    public static ChatServerData create() {
        return new ChatServerDataImpl();
    }

    @Override
    public Collection<String> getTakenUsernames() {
        return takenUsernames;
    }
}
