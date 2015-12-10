package de.haw_chat.server.network.Exceptions;

/**
 * Created by dima on 10.11.15.
 */
public class ChatroomAlreadyExistingExeption extends Exception {

    public ChatroomAlreadyExistingExeption(String message){
        super(message);
    }

    public ChatroomAlreadyExistingExeption(){
        super();
    }
}
