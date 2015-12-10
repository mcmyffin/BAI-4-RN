package de.haw_chat.server.network.Exceptions;

/**
 * Created by dima on 10.11.15.
 */
public class ChatroomIsFullExeption extends Exception {

    public ChatroomIsFullExeption(String message){
        super(message);
    }

    public ChatroomIsFullExeption(){
        super();
    }
}
