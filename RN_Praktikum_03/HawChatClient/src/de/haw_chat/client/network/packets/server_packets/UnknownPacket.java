package de.haw_chat.client.network.packets.server_packets;

import com.google.common.base.Joiner;
import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import java.util.Arrays;
import java.util.regex.Pattern;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class UnknownPacket extends AbstractServerPacket {
    private String messageString;

    public UnknownPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);

        messageString = messageString.replace("999 ","");
    }

    @Override
    public void process() {
        System.err.println("Unbekanntes Paket: " + messageString);
    }
}
