package bai4_rn.praktikum_01.client;

import java.net.Socket;

public interface ClientData {
    public Socket getClientSocket();
    public void setClientSocket(Socket clientSocket);
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
}
