package de.haw_chat.server.network.Exceptions;

/**
 * Created by dima on 10.11.15.
 */
public class UsernameNotFoundException extends Exception {

    public UsernameNotFoundException(String message){
        super(message);
    }

    public UsernameNotFoundException(){
        super();
    }
}
