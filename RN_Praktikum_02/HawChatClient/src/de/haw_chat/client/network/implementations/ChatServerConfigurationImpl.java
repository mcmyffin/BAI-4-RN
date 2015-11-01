package de.haw_chat.client.network.implementations;

import de.haw_chat.client.network.interfaces.ChatServerConfiguration;

import java.util.Properties;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Andreas on 31.10.2015.
 */
final class ChatServerConfigurationImpl implements ChatServerConfiguration {
    private final int serverPort;
    private final int maxThreads;
    private final boolean sslEnabled;

    private ChatServerConfigurationImpl(int serverPort, int maxThreads, boolean sslEnabled) {
        this.serverPort = serverPort;
        this.maxThreads = maxThreads;
        this.sslEnabled = sslEnabled;
    }

    public static ChatServerConfiguration create(int serverPort, int maxThreads, boolean sslEnabled) {
        return new ChatServerConfigurationImpl(serverPort, maxThreads, sslEnabled);
    }

    public static ChatServerConfiguration create(Properties properties) {
        checkNotNull(properties, "properties is null!");
        checkArgument(properties.containsKey("SERVER_PORT"), "properties does not contain key 'SERVER_PORT'!");
        checkArgument(properties.containsKey("MAX_THREADS"), "properties does not contain key 'MAX_THREADS'!");
        checkArgument(properties.containsKey("SSL_ENABLED"), "properties does not contain key 'SSL_ENABLED'!");

        int serverPort = Integer.parseInt(properties.getProperty("SERVER_PORT"));
        int maxThreads = Integer.parseInt(properties.getProperty("MAX_THREADS"));
        boolean sslEnabled = Boolean.parseBoolean(properties.getProperty("SSL_ENABLED"));

        return create(serverPort, maxThreads, sslEnabled);
    }

    @Override
    public int getServerPort() {
        return serverPort;
    }

    @Override
    public int getMaxThreads() {
        return maxThreads;
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
        if (getMaxThreads() != that.getMaxThreads()) return false;
        return isSslEnabled() == that.isSslEnabled();

    }

    @Override
    public int hashCode() {
        int result = getServerPort();
        result = 31 * result + getMaxThreads();
        result = 31 * result + (isSslEnabled() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatServerConfiguration{" +
                "serverPort=" + serverPort +
                ", maxThreads=" + maxThreads +
                ", sslEnabled=" + sslEnabled +
                '}';
    }
}
