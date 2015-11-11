package de.haw_chat.client.controllers;

import de.haw_chat.client.network.implementations.ChatDeviceFactory;
import de.haw_chat.client.network.interfaces.ChatClient;
import de.haw_chat.client.network.interfaces.ChatServerConfiguration;
import de.haw_chat.client.network.packets.client_packets.LoginPacket;
import de.haw_chat.client.views.MainFrame;

import javax.swing.*;
import java.io.IOException;

public class MainController {
	private ChatClient chatClient;

	public MainController(MainFrame frame) {
		
	}
	
	public void setServerConfiguration(String hostname, int port, boolean enableSsl) {
		ChatServerConfiguration configuration =
				ChatDeviceFactory.createChatServerConfiguration(hostname, port, enableSsl);
		chatClient = ChatDeviceFactory.createChatClient(configuration);

		Thread thread = new Thread(chatClient);
		thread.start();
	}
	
	public void login(String username, String password) {
		try {
			chatClient.getChatServerThread().writeToServer(new LoginPacket(username, password));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
