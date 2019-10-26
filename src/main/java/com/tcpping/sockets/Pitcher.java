package com.tcpping.sockets;

import com.tcpping.models.Message;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Pitcher {
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

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
        try {
            socket = new Socket(ip, port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(out);
            objectInputStream = new ObjectInputStream(in);

        } catch (IOException u) {
            throw new Error(u);
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(runPitcher(), 0, 1000);

        String message = "";

        while (!message.equals("OVER")) {
            try {
                Message receivedMessage = (Message) objectInputStream.readObject();
                if (receivedMessage != null) {
                    message = receivedMessage.message;

                    receivedMessage.setTimeMessageReturned((int) System.currentTimeMillis());

                    System.out.println(receivedMessage.message);
                    System.out.println(receivedMessage.timeMessageCreated);
                    System.out.println(receivedMessage.timeMessageArrived);
                    System.out.println(receivedMessage.timeMessageReturned);
                }
            } catch (IOException | ClassNotFoundException i) {
                throw new Error(i);
            }
        }
    }
}