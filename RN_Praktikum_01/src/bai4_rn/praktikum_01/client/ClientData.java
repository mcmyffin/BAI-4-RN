package bai4_rn.praktikum_01.client;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public interface ClientData {
    public String getMailAddress();
    public void setMailAddress(String mailAddress);
    public String getUsername();
    public void setUsername(String username);
    public String getPassword();
    public void setPassword(String password);
    public String getHostname();
    public void setHostname(String hostname);
    public int getPort();
    public void setPort(int port);

    public void writeToServer(String request) throws IOException;
    public String readFromServer() throws IOException;
    public List<String> readRemainingFromServer() throws IOException;
}
