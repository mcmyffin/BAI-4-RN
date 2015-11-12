package de.haw_chat.server.model;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.network.Exceptions.*;
import de.haw_chat.server.network.implementations.ServerData;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.*;

import java.security.Timestamp;
import java.util.*;

import static com.google.common.base.Preconditions.*;

/**
 * Created by dima on 04.11.15.
 */
public class Chatroom {

    private static final int defaultMaxUser = 2;
    private long id;
    private String owner;
    private String chatName;

    private String password;
    private int maxUser;
    private Map<String,ClientThread> connectedClients;


    private Chatroom(ClientThread clientThread, String chatName, String password, int maxUser){
        this.id         = System.currentTimeMillis();
        this.owner      = clientThread.getData().getUsername();
        this.chatName   = chatName;
        this.password   = password;
        this.maxUser    = maxUser;
        connectedClients.put(owner,clientThread);
    }

    public synchronized static Chatroom create(ClientThread clientThread, String chatName, String password, int maxUser)
            throws InvalidMaxUserSizeException, ChatroomAlreadyExistingExeption {
        // preconditions
        checkNotNull(clientThread);
        checkNotNull(chatName);
        checkNotNull(password);
        if(maxUser <= defaultMaxUser) throw new InvalidMaxUserSizeException();

        // check for chatroomName already taken
        ServerData serverData = clientThread.getServer().getData();
        if(serverData.isChatroomExisting(chatName)) throw new ChatroomAlreadyExistingExeption(chatName);

        return new Chatroom(clientThread,chatName,password,maxUser);
    }

    // getter
    public synchronized String getName(){
        return this.chatName;
    }

    public synchronized int getNumberOfConnectedUser(){
        return connectedClients.size();
    }

    public synchronized Set<String> getConnectedUsernames(){
        return Collections.unmodifiableSet(connectedClients.keySet());
    }

    public synchronized void join(ClientThread client, String password){
        checkNotNull(client);
        checkNotNull(password);
        Status ResponseStatus = Status.OK;

        if(connectedClients.containsKey(client.getData().getUsername())); // User already logged in

        if(!checkPassword(password))              ResponseStatus = Status.CHATROOM_LOGIN_WRONG_PASSWORD;
        if(getNumberOfConnectedUser() >= maxUser) ResponseStatus = Status.CHATROOM_IS_FULL;
        else {
            // add client to Chatroom
            connectedClients.put(client.getData().getUsername(), client);
            // SEND NEW MEMBERLIST TO MEMBERS
            sendDistributedMemberList();
        }
        // SEND JOIN_RESPONSE TO CLIENT
        ChatroomJoinResponsePacket packet = new ChatroomJoinResponsePacket(ResponseStatus);
        sendSinglePacketAt(client,packet);
    }


    public void recieveMessageFrom(ClientThread client, String message){
        checkNotNull(client);
        checkNotNull(message);

        Status responseStatus = Status.OK;
        if(!isMember(client)) responseStatus = Status.CHATROOM_NOT_MEMBER;
        else{
            sendDistributedMessage(client,message);
        }
        MessageSendResponsePacket packet = new MessageSendResponsePacket(responseStatus);
        sendSinglePacketAt(client,packet);
    }


    public synchronized void leave(ClientThread client) throws UserNotMemberExeption {
        checkNotNull(client);
        Status responseStatus = Status.OK;
        if(!connectedClients.containsKey(client.getData().getUsername())) responseStatus = Status.CHATROOM_NOT_MEMBER;
        else{
            // remove client from Chatroom
            connectedClients.remove(client.getData().getUsername());
            // send new Memberlist to chatroom member
            sendDistributedMemberList();
        }
        // send leave_presponse to client
        ChatroomLeaveResponsePacket packet = new ChatroomLeaveResponsePacket(responseStatus);
        sendSinglePacketAt(client,packet);
    }

    public synchronized void changeMaxUserCount(int newMaxUser, ClientThread client){
        checkNotNull(client);
        Status responseStatus = Status.OK;
        if(!isMember(client)) responseStatus = Status.CHATROOM_NOT_MEMBER;
        if(!isOwner(client))  responseStatus = Status.CHATROOM_PERMISSION_DENIED;
        if(newMaxUser < defaultMaxUser) responseStatus = Status.CHATROOM_MEMBER_COUNT_INVALID;
        if(newMaxUser < getNumberOfConnectedUser()) responseStatus = Status.CHATROOM_MEMBER_COUNT_INVALID;
        else{
            this.maxUser = newMaxUser;
        }
        ChatroomChangeMaxUserCountResponsePacket packet = new ChatroomChangeMaxUserCountResponsePacket(responseStatus);
        sendSinglePacketAt(client, packet);

    }

    public void changeChatName(String newChatName, String password, ClientThread client){
        checkNotNull(newChatName);
        checkNotNull(password);

        Status responseStatus = Status.OK;
        ServerData serverData = client.getServer().getData();
        String oldChatName    = this.chatName;

        if(owner != client.getData().getUsername()) responseStatus = Status.CHATROOM_PERMISSION_DENIED;
        else if(!connectedClients.containsKey(client.getData().getUsername())) responseStatus = Status.CHATROOM_NOT_MEMBER;
        else if(!checkPassword(password)) responseStatus = Status.PASSWORD_INVALID;
        else if(!serverData.renameChatRoom(this,newChatName)) responseStatus = Status.CHATROOM_NAME_ALREADY_TAKEN;
        else{
            this.chatName = newChatName;
            ChatroomNameChangedPacket packet = new ChatroomNameChangedPacket(oldChatName,newChatName);
            sendDistributedPackestAtAll(packet);
        }
        ChatroomChangeNameResponsePacket packet = new ChatroomChangeNameResponsePacket(responseStatus);
        sendSinglePacketAt(client,packet);

    }

    public synchronized void changePassword(String password, ClientThread client) {
        checkNotNull(password);
        checkNotNull(client);
        Status respoStatus = Status.OK;

        if(owner != client.getData().getUsername()) respoStatus = Status.CHATROOM_PERMISSION_DENIED;
        if(!connectedClients.containsKey(client.getData().getUsername())) respoStatus = Status.CHATROOM_NOT_MEMBER;
        if(!checkPassword(password)) respoStatus = Status.PASSWORD_INVALID;
        else{
            this.password = password;
        }
        ChatroomChangePasswordResponsePacket packet = new ChatroomChangePasswordResponsePacket(respoStatus);
        sendSinglePacketAt(client,packet);
    }

    private boolean checkPassword(String password){
        return this.password == password;
    }

    private synchronized boolean isMember(ClientThread client){
        return connectedClients.containsKey(client.getData().getUsername());
    }
    private synchronized boolean isOwner(ClientThread client){
        return owner.equals(client.getData().getUsername());
    }


    /***** Chatromm member push message methods *****/
    /**
     * Send AbstractServerPacket at all members
     * @param packet
     */
    private void sendDistributedPackestAtAll(AbstractServerPacket packet){
        for(ClientThread client : connectedClients.values()){
            client.writeToClient(packet);
        }
    }

    private void sendDistributedPacketsAtAll(List<AbstractServerPacket> packetList){
        for(AbstractServerPacket p : packetList){
            sendDistributedPackestAtAll(p);
        }
    }

    /**
     * Send AbstractServerPacket at all members without
     * without = ClientThread
     * @param packet
     * @param without
     */
    private void sendDistributedPacketsAtAll(AbstractServerPacket packet, ClientThread without){
        for(ClientThread client : connectedClients.values()){
            if(without.getData().getUsername().equals(client.getData().getUsername())) continue;
            client.writeToClient(packet);
        }
    }

    /**
     * Send AbstractServerPacket at single member
     * @param client
     * @param packet
     */
    private void sendSinglePacketAt(ClientThread client, AbstractServerPacket packet){
        client.writeToClient(packet);
    }

    /***************************************************/

    private void sendDistributedMemberList(){
        ChatroomMemberListBeginPacket packet_begin = new ChatroomMemberListBeginPacket(getName(),getNumberOfConnectedUser());
        ChatroomMemberListEndPacket   packet_end = new ChatroomMemberListEndPacket(getName());
        List<AbstractServerPacket> memberPackets = new ArrayList(getNumberOfConnectedUser());

        for(String username : connectedClients.keySet()){
            ChatroomMemberListElementPacket packet = new ChatroomMemberListElementPacket(getName(),username);
            memberPackets.add(packet);
        }

        sendDistributedPackestAtAll(packet_begin);
        sendDistributedPacketsAtAll(memberPackets);
        sendDistributedPackestAtAll(packet_end);
    }

    private void sendDistributedMessage(ClientThread messageFrom, String message){
        long timeStamp = System.currentTimeMillis();
        MessageSendedPacket packet = new MessageSendedPacket(messageFrom.getData().getUsername(),message,timeStamp); // TODO: MISSING CHATROOMNAME
        sendDistributedPacketsAtAll(packet,messageFrom);
    }

    @Override
    public String toString() {
        return getName();
    }
}
