package de.haw_chat.server.network.Exceptions;

/**
 * Created by dima on 10.11.15.
 */
public class InvalidMaxUserSizeException extends Exception {

    public InvalidMaxUserSizeException(String message){
        super(message);
    }

    public InvalidMaxUserSizeException(){
        super();
    }
}
