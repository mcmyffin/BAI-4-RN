package de.haw_chat.client.network.implementations;

import de.haw_chat.client.network.interfaces.ChatServerConfiguration;

import java.util.Properties;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatServerConfigurationImpl implements ChatServerConfiguration {
    private final String serverHost;
    private final int serverPort;
    private final boolean sslEnabled;

    private ChatServerConfigurationImpl(String serverHost, int serverPort, boolean sslEnabled) {
        checkNotNull(serverHost);
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.sslEnabled = sslEnabled;
    }

    public static ChatServerConfiguration create(String serverHost, int serverPort, boolean sslEnabled) {
        return new ChatServerConfigurationImpl(serverHost, serverPort, sslEnabled);
    }

    public static ChatServerConfiguration create(Properties properties) {
        checkNotNull(properties, "properties is null!");
        checkArgument(properties.containsKey("SERVER_HOST"), "properties does not contain key 'SERVER_HOST'!");
        checkArgument(properties.containsKey("SERVER_PORT"), "properties does not contain key 'SERVER_PORT'!");
        checkArgument(properties.containsKey("SSL_ENABLED"), "properties does not contain key 'SSL_ENABLED'!");

        String serverHost = properties.getProperty("SERVER_HOST");
        int serverPort = Integer.parseInt(properties.getProperty("SERVER_PORT"));
        boolean sslEnabled = Boolean.parseBoolean(properties.getProperty("SSL_ENABLED"));

        return create(serverHost, serverPort, sslEnabled);
    }

    @Override
    public String getServerHost() {
        return serverHost;
    }

    @Override
    public int getServerPort() {
        return serverPort;
    }

    @Override
    public boolean isSslEnabled() {
        return sslEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatServerConfigurationImpl)) return false;

        ChatServerConfigurationImpl that = (ChatServerConfigurationImpl) o;

        if (getServerPort() != that.getServerPort()) return false;
        if (isSslEnabled() != that.isSslEnabled()) return false;
        return getServerHost().equals(that.getServerHost());

    }

    @Override
    public int hashCode() {
        int result = getServerHost().hashCode();
        result = 31 * result + getServerPort();
        result = 31 * result + (isSslEnabled() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatServerConfiguration{" +
                "serverHost='" + serverHost + '\'' +
                ", serverPort=" + serverPort +
                ", sslEnabled=" + sslEnabled +
                '}';
    }
}
