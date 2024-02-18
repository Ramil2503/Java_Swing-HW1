package server;

import client.Client;

public interface ServerInterface {
    void receiveMessage(Client client, String message);
    boolean connectClient(Client client);
    boolean disconnectClient(Client client);
}
