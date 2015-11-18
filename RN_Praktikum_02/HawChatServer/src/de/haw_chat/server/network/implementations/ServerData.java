package de.haw_chat.server.network.implementations;

import de.haw_chat.server.model.Chatroom;
import de.haw_chat.server.network.Exceptions.ChatroomNotFoundExeption;
import de.haw_chat.server.network.Exceptions.UsernameNotFoundException;
import de.haw_chat.server.network.interfaces.ClientThread;

import java.util.*;

import static com.google.common.base.Preconditions.*;


/**
 * Created by Andreas on 31.10.2015.
 */
public final class ServerData {

    private Map<String,ClientThread> takenUsernames;
    private Map<String,Chatroom>            chatrooms;

    private ServerData() {
        this.takenUsernames = new HashMap();
        this.chatrooms = new HashMap();
    }

    public static ServerData create() {
        return new ServerData();
    }

    public synchronized boolean addUserClient(String username, ClientThread clientThread){
        checkNotNull(username);
        checkNotNull(clientThread);
        if(takenUsernames.containsKey(username)) return false;

        takenUsernames.put(username,clientThread);
        return true;
    }

    public synchronized boolean containsUser(String user){
        return takenUsernames.containsKey(user);
    }

    public synchronized boolean removeUserClient(ClientThread client){
        checkNotNull(client);
        if(!containsUser(client.getData().getUsername())) return false;

        takenUsernames.remove(client.getData().getUsername());
        client.getData().setUsername(null);
        return true;
    }

    public synchronized ClientThread getChatClientByUsername(String username) throws UsernameNotFoundException {
        checkNotNull(username);
        if(!containsUser(username)) throw new UsernameNotFoundException(username);
        return takenUsernames.get(username);
    }

    /** Chatroom operation **/
    public synchronized Set<String> getChatRoomNames(){
        return Collections.unmodifiableSet(chatrooms.keySet());
    }

    public synchronized Collection<Chatroom> getChatrooms(){
        return Collections.unmodifiableCollection(chatrooms.values());
    }

    public synchronized Chatroom getChatroomByName(String chatroom) throws ChatroomNotFoundExeption {
        checkNotNull(chatroom);
        if(!chatrooms.containsKey(chatroom)) throw new ChatroomNotFoundExeption(chatroom);
        return chatrooms.get(chatroom);
    }

    public synchronized boolean isChatroomExisting(String chatroomName){
        checkNotNull(chatroomName);
        return chatrooms.containsKey(chatroomName);
    }

    public synchronized boolean addChatroom(Chatroom chatroom){
        checkNotNull(chatroom);
        if(isChatroomExisting(chatroom.getName())) return false;
        chatrooms.put(chatroom.getName(), chatroom);
        return true;
    }

    public synchronized boolean removeChatroom(Chatroom chatroom){
        checkNotNull(chatroom);
        if(!isChatroomExisting(chatroom.getName())) return false;
        chatrooms.remove(chatroom.getName());
        return true;
    }

    public boolean renameChatroom(Chatroom chatroom, String newName){
        checkNotNull(chatroom);
        checkNotNull(newName);
        if(isChatroomExisting(newName)) return false;

        String oldName = chatroom.getName();
        chatrooms.remove(oldName);
        chatrooms.put(newName,chatroom);
        return true;
    }
}
