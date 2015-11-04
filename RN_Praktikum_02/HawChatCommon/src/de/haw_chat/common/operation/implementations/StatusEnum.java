package de.haw_chat.common.operation.implementations;

/**
 * Created by Andreas on 04.11.2015.
 */
public enum StatusEnum {
    OK,                                 // ALL PACKETS

    USERNAME_INVALID,                   // REGISTER_ACCOUNT_RESPONSE
    PASSWORD_INVALID,                   // REGISTER_ACCOUNT_RESPONSE
    CHATROOM_NAME_INVALIED,             // CHATROOM_CREATE_RESPONSE, CHATROOM_CHANGE_NAME_RESPONSE
    CHATROOM_PASSWORD_INVALID,          // CHATROOM_CREATE_RESPONSE, CHATROOM_CHANGE_PASSWORD_RESPONSE
    CHATROOM_MEMBER_COUNT_INVALID,      // CHATROOM_CREATE_RESPONSE, CHATROOM_CHANGE_MAX_USER_COUNT_RESPONSE

    USERNAME_ALREADY_TAKEN,             // REGISTER_ACCOUNT_RESPONSE

    CLIENT_ALREADY_LOGGED_IN,           // LOGIN_RESPONSE
    USERNAME_ALREADY_LOGGED_IN,         // LOGIN_RESPONSE
    PASSWORD_WRONG,                     // LOGIN_RESPONSE

    CLIENT_NOT_LOGGED_IN,               // LOGOUT_RESPONSE

    CHATROOM_NOT_FOUND,                 // CHATROOM_JOIN_RESPONSE, CHATROOM_LEAVE_RESPONSE, CHATROOM_DELETE_RESPONSE, CHATROOM_CHANGE_NAME_RESPONSE, CHATROOM_CHANGE_PASSWORD_RESPONSE, CHATROOM_CHANGE_MAX_USER_COUNT_RESPONSE, MESSAGE_SEND_RESPONSE
    CHATROOM_IS_FULL,                   // CHATROOM_JOIN_RESPONSE
    CHATROOM_LOGIN_WRONG_PASSWORD,      // CHATROOM_JOIN_RESPONSE

    CHATROOM_NOT_MEMBER,                // CHATROOM_LEAVE_RESPONSE, CHATROOM_CHANGE_NAME_RESPONSE, CHATROOM_CHANGE_PASSWORD_RESPONSE, CHATROOM_CHANGE_MAX_USER_COUNT_RESPONSE, MESSAGE_SEND_RESPONSE
    CHATROOM_NAME_ALREADY_TAKEN,        // CHATROOM_CREATE_RESPONSE, CHATROOM_CHANGE_NAME_RESPONSE

    CHATROOM_PERMISSION_DENIED,         // CHATROOM_DELETE_RESPONSE, CHATROOM_CHANGE_NAME_RESPONSE, CHATROOM_CHANGE_PASSWORD_RESPONSE, CHATROOM_CHANGE_MAX_USER_COUNT_RESPONSE
    ;

    private static final int START_VALUE = 400;

    public static StatusEnum getStatus(int statusCode) {
        statusCode = statusCode - START_VALUE;
        return StatusEnum.values()[statusCode];
    }

    public static StatusEnum getStatus(String statusName) {
        return StatusEnum.valueOf(statusName);
    }

    public int getStatusCode() {
        return ordinal() + START_VALUE;
    }

    public String getStatusName() {
        return name();
    }
}
