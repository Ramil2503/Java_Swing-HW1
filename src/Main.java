import client.Client;
import server.Server;
import server.ServerInterface;

public class Main {
    public static void main(String[] args) {
        ServerInterface serverInterface = new Server();
        new Client(serverInterface);
        new Client(serverInterface);
    }
}