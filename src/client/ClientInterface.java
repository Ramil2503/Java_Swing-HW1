package client;

public interface ClientInterface {
    String getCustomerName();
    void appendLog(String text);
    void receiveMessage(String message);
}
