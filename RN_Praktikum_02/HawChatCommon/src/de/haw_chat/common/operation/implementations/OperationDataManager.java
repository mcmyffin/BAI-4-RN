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
        storeOperationData(CLIENT, 102, "Login");
        storeOperationData(SERVER, 103, "LoginResponse");
        storeOperationData(CLIENT, 104, "UsernameChange");
        storeOperationData(SERVER, 105, "UsernameChangeResponse");
        storeOperationData(CLIENT, 106, "Logout");
        storeOperationData(SERVER, 107, "LogoutResponse");
        storeOperationData(CLIENT, 108, "ChatroomsRefresh");
        storeOperationData(SERVER, 109, "ChatroomListBegin");
        storeOperationData(SERVER, 110, "ChatroomListElement");
        storeOperationData(SERVER, 111, "ChatroomListEnd");
        storeOperationData(CLIENT, 112, "ChatroomJoin");
        storeOperationData(SERVER, 113, "ChatroomJoinResponse");
        storeOperationData(CLIENT, 114, "ChatroomLeave");
        storeOperationData(SERVER, 115, "ChatroomLeaveResponse");
        storeOperationData(CLIENT, 116, "ChatroomCreate");
        storeOperationData(SERVER, 117, "ChatroomCreateResponse");
        storeOperationData(CLIENT, 118, "ChatroomDelete");
        storeOperationData(SERVER, 119, "ChatroomDeleteResponse");
        storeOperationData(CLIENT, 120, "ChatroomChangeName");
        storeOperationData(SERVER, 121, "ChatroomChangeNameResponse");
        storeOperationData(CLIENT, 122, "ChatroomChangePassword");
        storeOperationData(SERVER, 123, "ChatroomChangePasswordResponse");
        storeOperationData(CLIENT, 124, "ChatroomChangeMaxUserCount");
        storeOperationData(SERVER, 125, "ChatroomChangeMaxUserCountResponse");
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
