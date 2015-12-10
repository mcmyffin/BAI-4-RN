package de.haw_chat.client.network.implementations;

import de.haw_chat.client.controllers.MainController;
import de.haw_chat.client.network.interfaces.ChatClientData;
import de.haw_chat.client.views.MainFrame;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatClientDataImpl implements ChatClientData {
    private String username;
    private MainController mainController;

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

    @Override
    public MainController getMainController() {
        return mainController;
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
