import client.Client;
import server.Server;

public class Main {
    public static void main(String[] args) {
        Server serverWindow = new Server();
        new Client(serverWindow);
        new Client(serverWindow);
    }
}