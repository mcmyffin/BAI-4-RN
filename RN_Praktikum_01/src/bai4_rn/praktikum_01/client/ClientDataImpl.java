package bai4_rn.praktikum_01.client;

import java.io.IOException;
import java.net.Socket;

final class ClientDataImpl implements ClientData {
    private Socket clientSocket;
    private String mailAddress;
    private String username;
    private String password;
    private String hostname;
    private int port;

    public ClientDataImpl() {
    }

    @Override
    public String getMailAddress() {
        return mailAddress;
    }

    @Override
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getHostname() {
        return hostname;
    }

    @Override
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void writeToServer(String request) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String readFromServer() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientDataImpl)) return false;

        ClientDataImpl that = (ClientDataImpl) o;

        if (getPort() != that.getPort()) return false;
        if (getClientSocket() != null ? !getClientSocket().equals(that.getClientSocket()) : that.getClientSocket() != null)
            return false;
        if (getMailAddress() != null ? !getMailAddress().equals(that.getMailAddress()) : that.getMailAddress() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null)
            return false;
        return !(getHostname() != null ? !getHostname().equals(that.getHostname()) : that.getHostname() != null);

    }

    @Override
    public int hashCode() {
        int result = getClientSocket() != null ? getClientSocket().hashCode() : 0;
        result = 31 * result + (getMailAddress() != null ? getMailAddress().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getHostname() != null ? getHostname().hashCode() : 0);
        result = 31 * result + getPort();
        return result;
    }

    @Override
    public String toString() {
        return "ClientData{" +
                "clientSocket=" + clientSocket +
                ", mailAddress='" + mailAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", hostname='" + hostname + '\'' +
                ", port=" + port +
                '}';
    }
}
