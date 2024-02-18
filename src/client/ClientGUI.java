package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class ClientGUI extends JFrame{
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    
    private Client client;

    protected JPanel panelName;
    protected JTextArea messageField;
    protected JTextArea log;
    protected JTextArea fieldName;
    private JButton btnConnect, btnDisconnect;

    public ClientGUI(Client client) {
        this.client = client;
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        // setLocation(server.getX() - 500, server.getY());

        createPanel();
        setVisible(true);
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
                client.sendMessage();
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
                if (client.isConnected()) {
                    client.appendLog("You are connected already");
                } else {
                    client.connectToServer();
                }
            }
        });

        btnDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.disconnectServer();
            }
        });

        panelButtom.add(btnConnect);
        panelButtom.add(btnDisconnect);

        return panelButtom;
    }
}
