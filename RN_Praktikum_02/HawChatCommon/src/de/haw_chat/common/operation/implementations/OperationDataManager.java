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
        storeOperationData(CLIENT, 100, "UnknownClient");
        storeOperationData(SERVER, 101, "UnknownServer");
        storeOperationData(CLIENT, 102, "UnsupportedClient");
        storeOperationData(SERVER, 103, "UnsupportedServer");
        storeOperationData(CLIENT, 104, "PingToServer");
        storeOperationData(SERVER, 105, "PongFromServer");
        storeOperationData(SERVER, 106, "PingToClient");
        storeOperationData(CLIENT, 107, "PongFromClient");
        storeOperationData(CLIENT, 108, "RegisterAccount");
        storeOperationData(SERVER, 109, "RegisterAccountResponse");
        storeOperationData(CLIENT, 110, "Login");
        storeOperationData(SERVER, 111, "LoginResponse");
        storeOperationData(CLIENT, 112, "Logout");
        storeOperationData(SERVER, 113, "LogoutResponse");
        storeOperationData(CLIENT, 114, "ChatroomsRefresh");
        storeOperationData(SERVER, 115, "ChatroomListBegin");
        storeOperationData(SERVER, 116, "ChatroomListElement");
        storeOperationData(SERVER, 117, "ChatroomListEnd");
        storeOperationData(CLIENT, 118, "ChatroomJoin");
        storeOperationData(SERVER, 119, "ChatroomJoinResponse");
        storeOperationData(CLIENT, 120, "ChatroomLeave");
        storeOperationData(SERVER, 121, "ChatroomLeaveResponse");
        storeOperationData(CLIENT, 122, "ChatroomCreate");
        storeOperationData(SERVER, 123, "ChatroomCreateResponse");
        storeOperationData(CLIENT, 124, "ChatroomDelete");
        storeOperationData(SERVER, 125, "ChatroomDeleteResponse");
        storeOperationData(CLIENT, 126, "ChatroomChangeName");
        storeOperationData(SERVER, 127, "ChatroomChangeNameResponse");
        storeOperationData(CLIENT, 128, "ChatroomChangePassword");
        storeOperationData(SERVER, 129, "ChatroomChangePasswordResponse");
        storeOperationData(CLIENT, 130, "ChatroomChangeMaxUserCount");
        storeOperationData(SERVER, 131, "ChatroomChangeMaxUserCountResponse");
        storeOperationData(SERVER, 132, "ChatroomNameChanged");
        storeOperationData(SERVER, 133, "ChatroomMaxUserCountChanged");
        storeOperationData(SERVER, 134, "ChatroomMemberListBegin");
        storeOperationData(SERVER, 135, "ChatroomMemberListElement");
        storeOperationData(SERVER, 136, "ChatroomMemberListEnd");
        storeOperationData(CLIENT, 137, "MessageSend");
        storeOperationData(SERVER, 138, "MessageSendResponse");
        storeOperationData(SERVER, 139, "MessageSended");
    }

    public static OperationData getOperationData(int operationCode) {
        if (!operationCodeMap.containsKey(operationCode))
            return getOperationData("UnknownClient"); // TODO: Change in Client version!
        return operationCodeMap.get(operationCode);
    }

    public static OperationData getOperationData(String operationName) {
        if (!operationNameMap.containsKey(operationName))
            return getOperationData("UnknownClient"); // TODO: Change in Client version!
        return operationNameMap.get(operationName);
    }
}
