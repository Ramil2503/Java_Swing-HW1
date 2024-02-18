package server;

import client.ClientInterface;

public interface ServerInterface {
    void receiveMessage(ClientInterface client, String message);
    boolean connectClient(ClientInterface client);
    boolean disconnectClient(ClientInterface client);
}
