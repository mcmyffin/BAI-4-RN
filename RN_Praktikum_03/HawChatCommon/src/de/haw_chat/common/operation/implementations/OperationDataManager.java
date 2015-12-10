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
        storeOperationData(CLIENT, 100, "Login");
        storeOperationData(CLIENT, 199, "Logout");
        storeOperationData(CLIENT, 101, "MessageSend");
        storeOperationData(CLIENT, 102, "RequestChatroomMemberList");
        // Server
        storeOperationData(SERVER, 200, "UserLoggedIn");
        storeOperationData(SERVER, 299, "UserLoggedOut");
        storeOperationData(SERVER, 201, "MessageSended");
        storeOperationData(SERVER, 202, "ChatroomMemberList");
        storeOperationData(SERVER, 300, "UsernameInvalid");
        storeOperationData(SERVER, 301, "UsernameAlreadyLoggedIn");
        storeOperationData(SERVER, 302, "ClientNotLoggedIn");
        storeOperationData(SERVER, 999, "Unknown");
    }

    public static OperationData getOperationData(int operationCode) {
        if (!operationCodeMap.containsKey(operationCode))
            return getOperationData("Unknown");
        return operationCodeMap.get(operationCode);
    }

    public static OperationData getOperationData(String operationName) {
        if (!operationNameMap.containsKey(operationName))
            return getOperationData("Unknown");
        return operationNameMap.get(operationName);
    }
}
