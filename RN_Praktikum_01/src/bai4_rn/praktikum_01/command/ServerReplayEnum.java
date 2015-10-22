package bai4_rn.praktikum_01.command;

/**
 * Created by Andreas on 22.10.2015.
 */
public enum ServerReplayEnum implements ServerReply {
    SERVICE_READY(220),
    SERVICE_CLOSED(221),
    REQUEST_OKAY(250),
    START_INPUT(354);

    private int statusCode;

    ServerReplayEnum(int statusCode) {
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
