package de.haw_chat.common.operation.implementations;

import de.haw_chat.common.operation.interfaces.OperationData;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 30.10.2015.
 */
public final class OperationDataManager {
    private final static boolean CLIENT = true;
    private final static boolean SERVER = false;

    private static Map<Integer, OperationData> operationCodeMap;
    private static Map<String, OperationData> operationNameMap;

    static {
        operationCodeMap = new HashMap<>();
        operationNameMap = new HashMap<>();
        storeOperationDatas();
    }

    private static void storeOperationData(boolean clientOperation, int operationCode, String operationName) {
        checkArgument(!operationCodeMap.containsKey(operationCode), "duplicate operationCode '" + operationCode + "'!");
        checkArgument(!operationNameMap.containsKey(operationName), "duplicate operationName '" + operationName + "'!");

        OperationData operationData = OperationDataImpl.create(clientOperation, operationCode, operationName);
        operationCodeMap.put(operationCode, operationData);
        operationNameMap.put(operationName, operationData);
    }

    private static void storeOperationDatas() {
        // Client
        storeOperationData(CLIENT, 102, "UnknownClient");
        storeOperationData(CLIENT, 110, "Login");
        storeOperationData(CLIENT, 112, "Logout");
        storeOperationData(CLIENT, 114, "RequestChatroomList");
        storeOperationData(CLIENT, 118, "ChatroomJoin");
        storeOperationData(CLIENT, 120, "ChatroomLeave");
        storeOperationData(CLIENT, 122, "ChatroomCreate");
        storeOperationData(CLIENT, 124, "ChatroomDelete");
        storeOperationData(CLIENT, 126, "ChatroomChangeName");
        storeOperationData(CLIENT, 128, "ChatroomChangePassword");
        storeOperationData(CLIENT, 130, "ChatroomChangeMaxUserCount");
        storeOperationData(CLIENT, 137, "MessageSend");
        storeOperationData(CLIENT, 140, "RequestChatroomMemberList");
        // Server
        storeOperationData(SERVER, 103, "Unknown");
        storeOperationData(SERVER, 104, "Invalid");
        storeOperationData(SERVER, 111, "LoginResponse");
        storeOperationData(SERVER, 113, "LogoutResponse");
        storeOperationData(SERVER, 115, "ChatroomListBegin");
        storeOperationData(SERVER, 116, "ChatroomListElement");
        storeOperationData(SERVER, 117, "ChatroomListEnd");
        storeOperationData(SERVER, 119, "ChatroomJoinResponse");
        storeOperationData(SERVER, 121, "ChatroomLeaveResponse");
        storeOperationData(SERVER, 123, "ChatroomCreateResponse");
        storeOperationData(SERVER, 125, "ChatroomDeleteResponse");
        storeOperationData(SERVER, 127, "ChatroomChangeNameResponse");
        storeOperationData(SERVER, 129, "ChatroomChangePasswordResponse");
        storeOperationData(SERVER, 131, "ChatroomChangeMaxUserCountResponse");
        storeOperationData(SERVER, 132, "ChatroomNameChanged");
        storeOperationData(SERVER, 133, "ChatroomMaxUserCountChanged");
        storeOperationData(SERVER, 134, "ChatroomMemberListBegin");
        storeOperationData(SERVER, 135, "ChatroomMemberListElement");
        storeOperationData(SERVER, 136, "ChatroomMemberListEnd");
        storeOperationData(SERVER, 138, "MessageSendResponse");
        storeOperationData(SERVER, 139, "MessageSended");
    }

    public static OperationData getOperationData(int operationCode) {
        if (!operationCodeMap.containsKey(operationCode))
            return getOperationData("UnknownClient");
        return operationCodeMap.get(operationCode);
    }

    public static OperationData getOperationData(String operationName) {
        if (!operationNameMap.containsKey(operationName))
            return getOperationData("UnknownClient");
        return operationNameMap.get(operationName);
    }
}
