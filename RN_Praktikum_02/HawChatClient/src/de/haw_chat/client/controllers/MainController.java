package de.haw_chat.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.haw_chat.client.network.implementations.ChatDeviceFactory;
import de.haw_chat.client.network.interfaces.ChatClient;
import de.haw_chat.client.network.interfaces.ChatServerConfiguration;
import de.haw_chat.client.network.packets.client_packets.ChatroomCreatePacket;
import de.haw_chat.client.network.packets.client_packets.LoginPacket;
import de.haw_chat.client.network.packets.client_packets.LogoutPacket;
import de.haw_chat.client.views.MainFrame;

public class MainController {
	private MainFrame frame;
	private ChatClient chatClient;

	public MainController(MainFrame frame) {
		this.frame = frame;
	}

	public MainFrame getFrame() {
		return frame;
	}

	public ChatClient getChatClient() {
		return chatClient;
	}

	public void setServerConfiguration(String hostname, int port, boolean enableSsl) {
		ChatServerConfiguration configuration =
				ChatDeviceFactory.createChatServerConfiguration(hostname, port, enableSsl);
		chatClient = ChatDeviceFactory.createChatClient(configuration);

		Thread thread = new Thread(chatClient);
		thread.start();

		while (!chatClient.isStarted()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		chatClient.getData().setMainController(this);

		frame.buttonLogin.setEnabled(true);
	}

	public void login(String username, String password) {
		if (chatClient.getData().getMainController().getFrame().isLoggedIn()) {
			logout();
			return;
		}

		try {
			chatClient.getChatServerThread().writeToServer(new LoginPacket(username, password));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logout() {
		try {
			chatClient.getChatServerThread().writeToServer(new LogoutPacket());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	List<String> chatroomsToRemove = new ArrayList<>();

	public void processChatroomOverviewStart() {
		List<String> listElements = Collections.list(frame.listModel.elements());
		chatroomsToRemove.addAll(listElements);
	}

	public void processChatroomOverviewElement(String chatroom) {
		chatroomsToRemove.remove(chatroom);
		addChatroomToList(chatroom);
	}

	public void processChatroomOverviewEnd() {
		for (String chatroom : chatroomsToRemove) {
			deleteChatroomFromList(chatroom);
		}
		chatroomsToRemove.clear();
	}

	public void addChatroomToList(String name) {
		if (!frame.listModel.contains(name))
			frame.listModel.addElement(name);
	}

	public void deleteChatroomFromList(String name) {
		if (frame.listModel.contains(name))
			frame.listModel.removeElement(name);
	}



	private Map<String, List<String>> usersToRemove = new HashMap<>();
	
	public void processChatroomListStart(String chatroom) {
		usersToRemove.put(chatroom, new ArrayList<>());
		
		List<String> listElements = Collections.list(frame.chatroomUsers.get(chatroom).elements());
		usersToRemove.get(chatroom).addAll(listElements);
	}
	
	public void processChatroomListElement(String chatroom, String user) {
		usersToRemove.get(chatroom).remove(user);
		addUserToList(chatroom, user);
	}
	
	public void processChatroomListEnd(String chatroom) {
		for (String user : usersToRemove.get(chatroom)) {
			deleteUserFromList(chatroom, user);
		}
		usersToRemove.get(chatroom).clear();
	}

	private void addUserToList(String chatroom, String user) {
		if (!frame.getChatroomUsers(chatroom).contains(user))
			frame.getChatroomUsers(chatroom).addElement(user);
	}
	
	private void deleteUserFromList(String chatroom, String user) {
		if (frame.getChatroomUsers(chatroom).contains(user))
			frame.getChatroomUsers(chatroom).removeElement(user);
	}
	


	
	
	
	public void joinChatroom(String name) {
		frame.addChatroomPanel(name);
		frame.gotoChatroomPanel();
	}
	
	public void leaveChatroom() {
		frame.removeCurrentChatroomPanel();
	}
	
	public void createChatroom(String name, String password, int maxUserCount) {
		joinChatroom(name);
	}
	
	
	
	public void receiveMessage(String chatroom, String user, String message) {
		frame.getChatroomMessages(chatroom).addElement("[" + user + "]: " + message);
	}
	
	
	
	
	public void requestSendMessage(String chatroom, String message) {
		System.out.println("TODO send: " + chatroom + "   " + message);
	}
	
	public void requestJoinChatroom(String name) {
		System.out.println("TODO join: " + name);
	}
	
	public void requestCreateChatroom(String name, String password, int maxUserCount) {
		try {
			chatClient.getChatServerThread().writeToServer(new ChatroomCreatePacket(name, maxUserCount, password));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void requestLeaveChatroom(String chatroom) {
		System.out.println("TODO leave: " + chatroom);
	}
}
