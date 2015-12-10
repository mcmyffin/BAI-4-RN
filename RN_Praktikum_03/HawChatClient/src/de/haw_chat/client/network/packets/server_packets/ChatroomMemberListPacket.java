package de.haw_chat.client.network.packets.server_packets;

import de.haw_chat.client.network.interfaces.ChatServerThread;
import de.haw_chat.common.operation.implementations.Status;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static de.haw_chat.common.operation.implementations.Status.*;

/**
 * Created by Andreas on 31.10.2015.
 */
public class ChatroomMemberListPacket extends AbstractServerPacket {
    private List<String> members;

    public ChatroomMemberListPacket(ChatServerThread chatServerThread, String messageString) {
        super(chatServerThread);
        this.members = Arrays.asList(messageString.replaceFirst(Pattern.quote("202 "), "").split(","));
    }

    @Override
    public void process() {
        getClientData().getMainController().processChatroomList(members);
    }
}
