package de.haw_chat.server.model;

import de.haw_chat.common.operation.implementations.Status;
import de.haw_chat.server.network.implementations.ServerData;
import de.haw_chat.server.network.interfaces.ClientThread;
import de.haw_chat.server.network.packets.server_packets.*;

import java.util.*;

import static com.google.common.base.Preconditions.*;

/**
 * Created by dima on 04.11.15.
 */
public class Chatroom {

    private static final int defaultMaxUser = 2;
    private static final int minChatroomNameCharCount = 3;
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
        this.connectedClients = new HashMap();
        this.connectedClients.put(owner, clientThread);

    }

    public synchronized static void create(ClientThread client, String chatName, String password, int maxUser){

        // preconditions
        checkNotNull(client);
        checkNotNull(chatName);
        checkNotNull(password);

        Status response = Status.OK;
        ServerData serverData = client.getServer().getData();
        Chatroom chatroom = new Chatroom(client,chatName,password,maxUser);

        if(maxUser < defaultMaxUser) response = Status.CHATROOM_MEMBER_COUNT_INVALID;
        else if(chatName.length() < minChatroomNameCharCount) response = Status.CHATROOM_NAME_INVALIED;
        else if(serverData.isChatroomExisting(chatName)) response = Status.CHATROOM_NAME_ALREADY_TAKEN;
        else if(!serverData.addChatroom(chatroom)) response = Status.CHATROOM_NAME_ALREADY_TAKEN;
        else{
            chatroom.sendDistributedMemberList();
        }
        ChatroomCreateResponsePacket packet = new ChatroomCreateResponsePacket(response);
        sendSinglePacketAt(client, packet);
    }

    public synchronized void remove(ClientThread client){
        checkNotNull(client);
        Status response = Status.OK;
        if(!isOwner(client)) response = Status.CHATROOM_PERMISSION_DENIED;
        else if(!isMember(client)) response = Status.CHATROOM_NOT_MEMBER;
        else{

            // disconnect all member
            for(ClientThread c : connectedClients.values()){
                leave(c);
            }

            boolean result = client.getServer().getData().removeChatroom(this);
            if(!result) response = Status.CHATROOM_NOT_FOUND;

            ChatroomDeleteResponsePacket packet = new ChatroomDeleteResponsePacket(response);
            sendSinglePacketAt(client,packet);
        }
    }

    // getter
    public synchronized String getName(){
        return this.chatName;
    }

    public synchronized int getMaxUser(){
        return new Integer(this.maxUser);
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

        if(isMember(client)); // User already logged in

//        if(!checkPassword(password))              ResponseStatus = Status.CHATROOM_LOGIN_WRONG_PASSWORD;
        else if(getNumberOfConnectedUser() >= maxUser) ResponseStatus = Status.CHATROOM_IS_FULL;
        else {
            // add client to Chatroom
            connectedClients.put(client.getData().getUsername(), client);
            client.getData().addConnectedChats(getName());

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


    public synchronized void leave(ClientThread client){
        checkNotNull(client);
        Status responseStatus = Status.OK;
        if(!connectedClients.containsKey(client.getData().getUsername())) responseStatus = Status.CHATROOM_NOT_MEMBER;
        else{
            // remove client from Chatroom
            connectedClients.remove(client.getData().getUsername());
            client.getData().disconnectFromChat(getName());

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

    public void changeChatName(String newChatName, ClientThread client){
        checkNotNull(newChatName);

        Status responseStatus = Status.OK;
        ServerData serverData = client.getServer().getData();
        String oldChatName    = this.chatName;

        if(owner != client.getData().getUsername()) responseStatus = Status.CHATROOM_PERMISSION_DENIED;
        else if(!connectedClients.containsKey(client.getData().getUsername())) responseStatus = Status.CHATROOM_NOT_MEMBER;
        else if(!serverData.renameChatroom(this, newChatName)) responseStatus = Status.CHATROOM_NAME_ALREADY_TAKEN;
        else{
            this.chatName = newChatName;

            // change chatName in Clientdata by connected members
            for(ClientThread c : connectedClients.values()){
                c.getData().disconnectFromChat(oldChatName);
                c.getData().addConnectedChats(newChatName);
            }

            ChatroomNameChangedPacket packet = new ChatroomNameChangedPacket(oldChatName,newChatName);
            sendDistributedPacketsAtAll(packet);
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
    private void sendDistributedPacketsAtAll(AbstractServerPacket packet){
        for(ClientThread client : connectedClients.values()){
            client.writeToClient(packet);
        }
    }

    private void sendDistributedPacketsAtAll(List<AbstractServerPacket> packetList){
        for(AbstractServerPacket p : packetList){
            sendDistributedPacketsAtAll(p);
        }
    }

//    /**
//     * Send AbstractServerPacket at all members without
//     * without = ClientThread
//     * @param packet
//     * @param without
//     */
//    private void sendDistributedPacketsAtAll(AbstractServerPacket packet, ClientThread without){
//        for(ClientThread client : connectedClients.values()){
//            if(without.getData().getUsername().equals(client.getData().getUsername())) continue;
//            client.writeToClient(packet);
//        }
//    }

    /**
     * Send AbstractServerPacket at single member
     * @param client
     * @param packet
     */
    private static void sendSinglePacketAt(ClientThread client, AbstractServerPacket packet){
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

        sendDistributedPacketsAtAll(packet_begin);
        sendDistributedPacketsAtAll(memberPackets);
        sendDistributedPacketsAtAll(packet_end);
    }

    private void sendDistributedMessage(ClientThread messageFrom, String message){
        long timeStamp = System.currentTimeMillis();
        MessageSendedPacket packet = new MessageSendedPacket(getName(),messageFrom.getData().getUsername(),message,timeStamp); // TODO: MISSING CHATROOMNAME
        sendDistributedPacketsAtAll(packet);
    }

    @Override
    public String toString() {
        return getName();
    }
}
