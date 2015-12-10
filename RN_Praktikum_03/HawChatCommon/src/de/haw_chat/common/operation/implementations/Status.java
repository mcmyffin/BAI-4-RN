package de.haw_chat.common.operation.implementations;

/**
 * Created by Andreas on 04.11.2015.
 */
public enum Status {
    OK;

    private static final int START_VALUE = 400;

    public static Status getStatus(int statusCode) {
        statusCode = statusCode - START_VALUE;
        return Status.values()[statusCode];
    }

    public static Status getStatus(String statusName) {
        return Status.valueOf(statusName);
    }

    public int getStatusCode() {
        return ordinal() + START_VALUE;
    }

    public String getStatusName() {
        return name();
    }
}
