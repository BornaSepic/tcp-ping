package com.tcpping.sockets;

import com.tcpping.models.Message;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Pitcher {
    private DataOutputStream out;
    private ObjectOutputStream objectOutputStream;

    private int messagesSent = 0;
    private int messagesPerSecond = 30;

    private void sendMessages() throws IOException {

        for (int i = 0; i < messagesPerSecond; i++) {
            Message messageInstance = new Message(10);
            objectOutputStream.writeObject(messageInstance);
            messagesSent++;
        }

    }

    private TimerTask runPitcher() {
        return new TimerTask() {
            public void run() {
                try {
                    sendMessages();
                    System.out.println(messagesSent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void Client(String ip, int port, int mps) {
        messagesPerSecond = mps;
        Socket socket;
        BufferedReader input;
        try {
            socket = new Socket(ip, port);
            input = new BufferedReader(new InputStreamReader(System.in));
            out = new DataOutputStream(socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(out);
        } catch (IOException u) {
            throw new Error(u);
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(runPitcher(), 0, 1000);
    }
}