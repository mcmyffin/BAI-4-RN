package de.haw_chat.client.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.haw_chat.client.views.MainFrame;

public class MainController {
	private MainFrame frame;
	
	public MainController(MainFrame frame) {
		this.frame = frame;
	}
	
	public void setServerConfiguration(String hostname, int port, boolean enableSsl) {
		System.out.println("TODO set config");
	}
	
	public void login(String username, String password) {
		System.out.println("TODO login");
	}
	
	
	
	
	private Map<String, List<String>> usersToRemove = new HashMap<>();
	
	public void processChatroomListStart(String chatroom) {
		usersToRemove.put(chatroom, new ArrayList<>());
		
		List<String> listElements = Collections.list(frame.listModel.elements());
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
	}
	
	
	
	
	
	private void addUserToList(String chatroom, String user) {
		if (!frame.getChatroomUsers(chatroom).contains(user))
			frame.getChatroomUsers(chatroom).addElement(user);
	}
	
	private void deleteUserFromList(String chatroom, String user) {
		if (frame.getChatroomUsers(chatroom).contains(user))
			frame.getChatroomUsers(chatroom).removeElement(user);
	}
	
	
	
	
	
	public void addChatroomToList(String name) {
		if (!frame.listModel.contains(name))
			frame.listModel.addElement(name);
	}
	
	public void deleteChatroomFromList(String name) {
		if (frame.listModel.contains(name))
			frame.listModel.removeElement(name);
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
		System.out.println("TODO create: " + name + "   " + password + "   " + maxUserCount);
	}

	public void requestLeaveChatroom(String chatroom) {
		System.out.println("TODO leave: " + chatroom);
	}
}
