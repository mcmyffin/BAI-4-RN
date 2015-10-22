package bai4_rn.praktikum_01.command;

/**
 * Created by Andreas on 22.10.2015.
 */
public enum ServerReplyEnum implements ServerReply {
    SERVICE_READY(220),
    SERVICE_CLOSED(221),
    AUTH_SUCCESSFUL(235),
    REQUEST_OKAY(250),
    AUTH_DATA_OKAY(334),
    START_INPUT(354),
    COMMAND_NOT_RECOGNIZED(502);

    private int statusCode;

    ServerReplyEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String toString() {
        return "ServerReplay{" +
                "statusCode=" + statusCode +
                ", name='" + getName() + '\'' +
                '}';
    }
}
