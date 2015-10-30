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
        storeOperationData(SERVER, 103, "UnknownServer");
    }

    public static OperationData getOperationData(int operationCode) {
        return operationCodeMap.get(operationCode);
    }

    public static OperationData getOperationData(String operationName) {
        return operationNameMap.get(operationName);
    }
}
