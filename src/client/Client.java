package client;

import server.ServerInterface;

public class Client implements ClientInterface{
    private ServerInterface server;
    private boolean connected;
    private ClientGUI clientGUI;

    private String customerName;

    public Client(ServerInterface server) {
        this.server = server;
        clientGUI = new ClientGUI(this);
    }

    public boolean isConnected() {
        return connected;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void receiveMessage(String message) {
        appendLog(message);
    }

    public void sendMessage() {
        if (connected) {
            server.receiveMessage(this, clientGUI.messageField.getText());
            clientGUI.messageField.setText(null);
        } 
    }

    public void appendLog(String text) {
        clientGUI.log.append(text + "\n");
    }

    public void connectToServer() {
        setCustomerName(clientGUI.fieldName.getText());
        if(server.connectClient(this)) {
            clientGUI.panelName.setVisible(false);
            clientGUI.log.setVisible(true);
            connected = true;
            appendLog("Successfully connected to the server");
        }
    }

    public void disconnectServer() {
        server.disconnectClient(this);
        connected = false;
        appendLog("Successfully disconnected the server");
        clientGUI.panelName.setVisible(true);
        clientGUI.log.setVisible(false);
    }
}
