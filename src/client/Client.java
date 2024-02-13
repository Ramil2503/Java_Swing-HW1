package client;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import server.Server;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Client extends JFrame{
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    
    private JPanel panelName;
    private JTextArea messageField;
    private Server server;
    private JTextArea log;
    private JTextArea fieldName;
    private JButton btnConnect, btnDisconnect;
    private boolean connected;

    private String customerName;

    public Client(Server server) {
        this.server = server;

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        setLocation(server.getX() - 500, server.getY());

        createPanel();
        setVisible(true);
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

    private void sendMessage() {
        if (connected) {
            server.receiveMessage(this, messageField.getText());
            messageField.setText(null);
        } 
    }

    private void appendLog(String text) {
        log.append(text + "\n");
    }

    private void connectToServer() {
        setCustomerName(fieldName.getText());
        if(server.connectClient(this)) {
            panelName.setVisible(false);
            log.setVisible(true);
            connected = true;
            appendLog("Successfully connected to the server");
        }
    }

    private void disconnectServer() {
        server.disconnectClient(this);
        connected = false;
        appendLog("Successfully disconnected the server");
        panelName.setVisible(true);
        log.setVisible(false);
    }

    private void createPanel() {
        log = new JTextArea();
        add(log);
        log.setVisible(false);

        add(createNameField(), BorderLayout.NORTH);
        add(createButtomWrapper(), BorderLayout.SOUTH);
    }

    private Component createNameField() {
        panelName = new JPanel(new GridLayout(1, 2));
        JLabel jl = new JLabel("Enter your name: ");
        fieldName = new JTextArea();
        panelName.add(jl);
        panelName.add(fieldName);
        return panelName;
    }

    private Component createButtomWrapper() {
        JPanel buttomWrapper = new JPanel(new GridLayout(2,1));
        buttomWrapper.add(createMessageField());
        buttomWrapper.add(createButtons());

        return buttomWrapper;
    }

    private Component createMessageField() {
        JPanel panelMessage = new JPanel(new GridBagLayout());
    
        messageField = new JTextArea();
        JButton btnSend = new JButton("Send");

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    
        GridBagConstraints gbcMessage = new GridBagConstraints();
        gbcMessage.fill = GridBagConstraints.HORIZONTAL;
        gbcMessage.weightx = 0.8;
        gbcMessage.gridx = 0;
        panelMessage.add(messageField, gbcMessage);
    
        GridBagConstraints gbcBtnSend = new GridBagConstraints();
        gbcBtnSend.fill = GridBagConstraints.HORIZONTAL;
        gbcBtnSend.weightx = 0.2;
        gbcBtnSend.gridx = 1;
        panelMessage.add(btnSend, gbcBtnSend);
    
        return panelMessage;
    }

    private Component createButtons() {
        JPanel panelButtom = new JPanel(new GridLayout(1,2));
        btnConnect = new JButton("Connect to the server");
        btnDisconnect = new JButton("Disconnect from the server");
        
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connected) {
                    appendLog("You are connected already");
                } else {
                    connectToServer();
                }
            }
        });

        btnDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnectServer();
            }
        });

        panelButtom.add(btnConnect);
        panelButtom.add(btnDisconnect);

        return panelButtom;
    }
}
