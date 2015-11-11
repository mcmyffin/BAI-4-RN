package de.haw_chat.server.network.implementations;

import de.haw_chat.server.network.Exceptions.UsernameNotFoundException;
import de.haw_chat.server.network.interfaces.ClientThread;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.*;


/**
 * Created by Andreas on 31.10.2015.
 */
public final class ServerData {

    private Map<String,ClientThread> takenUsernames;
    private ServerData() {
        this.takenUsernames = new HashMap();
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

    public synchronized ClientThread getChatClientByUsername(String username) throws UsernameNotFoundException {
        checkNotNull(username);
        if(!containsUser(username)) throw new UsernameNotFoundException(username);
        return takenUsernames.get(username);
    }
}
