package de.haw_chat.client.network.interfaces;

import de.haw_chat.client.controllers.MainController;

/**
 * Created by Andreas on 31.10.2015.
 */
public interface ChatClientData {
    MainController getMainController();
    void setMainController(MainController mainFrame);

    String getUsername();
    void setUsername(String username);
}
