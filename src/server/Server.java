package server;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import client.Client;
import file_writer.FileManager;

public class Server extends JFrame {
    private static final int WIDTH = 555;
    private static final int HEIGHT = 555;

    private List<Client> clientList;
    
    private String pathToLog = "src/server/serverlog.txt";
    private FileManager fileManager;

    private JButton btnStart, btnStop;
    private JTextArea log;
    private boolean work;

    public Server() {
        clientList = new ArrayList<>();
        fileManager = new FileManager(pathToLog);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        setTitle("Server");
        setResizable(false);

        createPanel();
        setVisible(true);
    }

    public boolean connectClient(Client client) {
        if (work) {
            addClient(client);
            appendLog("Client " + client.getCustomerName() + " connected to the server");
            client.appendLog(getLog());
            return true;
        } else {
            return false;
        }
    }
    
    public void receiveMessage(Client client, String message) {
        String fomrattedMessage = client.getCustomerName() + ": " + message;
        fileManager.saveInLog(fomrattedMessage);
        appendLog(fomrattedMessage);
        broadcastMessage(fomrattedMessage);
    }

    public void broadcastMessage(String message) {
        for (Client client : clientList) {
            client.receiveMessage(message);
        }
    }

    public boolean disconnectClient(Client client) {
        removeClient(client);
        appendLog("Client " + client.getCustomerName() + " disconnected from the server");
        return true;
    }

    private void addClient(Client client) {
        clientList.add(client);
    }

    private void removeClient(Client client) {
        clientList.remove(client);
    }

    private void appendLog(String text) {
        log.append(text + "\n");
    }

    private void createPanel() {
        log = new JTextArea();
        add(log);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private void loadLog() {
        appendLog(getLog());
    }

    private String getLog() {
        return fileManager.loadLog();
    }

    private Component createButtons() {
        btnStart = new JButton("start server");
        btnStop = new JButton("stop server");
        JPanel panBottom = new JPanel(new GridLayout(1,2));

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    
                if (work) {
                    appendLog("Server is already running");
                } else {
                    work = true;
                    loadLog();
                    appendLog("Server started");
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!work) {
                    appendLog("Server has already been stopped");
                } else {
                    work = false;
                    appendLog("Server stopped");
                }
            }
        });

        panBottom.add(btnStart);
        panBottom.add(btnStop);

        return panBottom;
    }
}
