package de.haw_chat.server.network.Exceptions;

/**
 * Created by dima on 10.11.15.
 */
public class UserNotMemberExeption extends Exception {

    public UserNotMemberExeption(String message){
        super(message);
    }

    public UserNotMemberExeption(){
        super();
    }
}
