package de.haw_chat.client.controllers;

import de.haw_chat.client.network.implementations.ChatDeviceFactory;
import de.haw_chat.client.network.interfaces.ChatClient;
import de.haw_chat.client.network.interfaces.ChatServerConfiguration;
import de.haw_chat.client.network.packets.client_packets.*;
import de.haw_chat.client.views.MainFrame;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainController {
	private MainFrame frame;
	private ChatClient chatClient;
	private Map<String, Long> timeStarted = new HashMap<>();

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


	List<String> chatroomsOld = new ArrayList<>();
	List<String> chatroomsNew = new ArrayList<>();

	public void processChatroomOverviewStart() {
		chatroomsOld = new ArrayList<>();
		List<String> listElements = Collections.list(frame.listModel.elements());
		chatroomsOld.addAll(listElements);

		chatroomsNew = new ArrayList<>();
	}

	public void processChatroomOverviewElement(String chatroom) {
		chatroomsNew.add(chatroom);
	}

	public void processChatroomOverviewEnd() {
		List<String> toAdd = new ArrayList<>(chatroomsNew);
		toAdd.removeAll(chatroomsOld);

		List<String> toRemove = new ArrayList<>(chatroomsOld);
		toRemove.removeAll(chatroomsNew);

		for (String chatroom : toRemove) {
			deleteChatroomFromList(chatroom);
		}

		for (String chatroom : toAdd) {
			addChatroomToList(chatroom);
		}
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

		if (!frame.chatroomUsers.containsKey(chatroom)) {
			frame.chatroomUsers.put(chatroom, new DefaultListModel<>());
		}
		
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
		if (!frame.getChatroomUsers(chatroom).contains(user)) {
			frame.getChatroomUsers(chatroom).addElement(user);

			boolean notJustStarted;
			if (timeStarted.containsKey(chatroom))
				notJustStarted = System.currentTimeMillis() > (timeStarted.get(chatroom) + 1000);
			else
				notJustStarted = false;

			if (!user.equals(chatClient.getData().getUsername()) && notJustStarted) {
				if (user.equals("unnamed")) {
					playSound("joined_unnamed.wav");
				} else {
					playSound("joined.wav");
				}
			}
		}
	}
	
	private void deleteUserFromList(String chatroom, String user) {
		if (frame.getChatroomUsers(chatroom).contains(user)) {
			frame.getChatroomUsers(chatroom).removeElement(user);
			if (!user.equals(chatClient.getData().getUsername()))
				playSound("leaved.wav");
		}
	}




	Map<String, Thread> refreshThreads = new HashMap<>();
	
	public void joinChatroom(String name) {
		timeStarted.put(name, System.currentTimeMillis());

		frame.addChatroomPanel(name);
		if (!name.equals("DefaultChatroom"))
			frame.gotoChatroomPanel();

		refreshThreads.put(name, new Thread() {
			@Override
			public void run() {

			}
		});
		refreshThreads.get(name).start();
	}
	
	public void leaveChatroom() {
		refreshThreads.get(frame.getChatroomName()).interrupt();
		frame.removeCurrentChatroomPanel();
		frame.gotoChatroomOverview();
	}

	public static synchronized void playSound(final String filename) {
		try{
			File file = new File("sounds/" + filename);
			System.out.println(file.exists());
			AudioInputStream audioInputStream =
					AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveMessage(String chatroom, String user, String message, long timestamp) {
		SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(timestamp);
		String time = printFormat.format(date);

		frame.getChatroomMessages(chatroom).addElement("[" + time + "] <" + user + "> " + message);
		if (!user.equals(chatClient.getData().getUsername()) && frame.chckbxEnableSounds.isSelected())
			playSound("post.wav");
	}
	
	
	
	
	public void requestSendMessage(String chatroom, String message) {
		try {
			chatClient.getChatServerThread().writeToServer(new MessageSendPacket(chatroom, message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void requestJoinChatroom(String name, String password) {
		try {
			chatClient.getChatServerThread().writeToServer(new ChatroomJoinPacket(name, password));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void requestCreateChatroom(String name, String password, int maxUserCount) {
		try {
			chatClient.getChatServerThread().writeToServer(new ChatroomCreatePacket(name, maxUserCount, password));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void requestLeaveChatroom(String chatroom) {
		try {
			chatClient.getChatServerThread().writeToServer(new ChatroomLeavePacket(chatroom));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
