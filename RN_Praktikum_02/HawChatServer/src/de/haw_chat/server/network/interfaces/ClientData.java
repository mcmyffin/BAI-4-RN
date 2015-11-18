package de.haw_chat.server.network.interfaces;

import java.util.Set;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface ClientData {

    String getUsername();
    void setUsername(String username);
    boolean isLoggedIn();

    Set<String> getConnectedChats();

    void addConnectedChats(String chatname);
    void disconnectFromChat(String chatname);
}
