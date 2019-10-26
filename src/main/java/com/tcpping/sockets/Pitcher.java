package com.tcpping.sockets;

import com.tcpping.models.Message;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;

public class Pitcher {
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private List<Message> messages = new ArrayList<Message>();

    private int messagesSent = 0;
    private int messagesPerSecond = 30;
    private int messageGroupsSent = 0;

    private void sendMessages() throws IOException {
        displayStatistics(messageGroupsSent);
        messageGroupsSent++;

        for (int i = 0; i < messagesPerSecond; i++) {
            Message messageInstance = new Message(10, messagesSent, messageGroupsSent);
            objectOutputStream.writeObject(messageInstance);
            messagesSent++;
        }
    }

    private TimerTask runPitcher() {
        return new TimerTask() {
            public void run() {
                try {
                    sendMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void displayStatistics(int messageGroupId) {
        List<Message> returnedMessages = new ArrayList<Message>(messages);
        List<Message> relevantMessages = new ArrayList<Message>();

        for (Message message : returnedMessages) {
            System.out.println(message.messageGroupId + " test " + messageGroupId);
            if (message.getMessageGroupId() == messageGroupId) {
                relevantMessages.add(message);
            }
        }

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Time: " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
        System.out.println("Total messages sent: " + messagesSent);
        System.out.println("Messages per second: " + messagesPerSecond);
        System.out.println("Average total trip time: " + relevantMessages.size());
        System.out.println("Average time to server: " + 1);
        System.out.println("Average time from server: " + 1);
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
        timer.scheduleAtFixedRate(runPitcher(), 1000, 1000);

        String message = "";

        while (!message.equals("OVER")) {
            try {
                Message receivedMessage = (Message) objectInputStream.readObject();
                if (receivedMessage != null) {
                    message = receivedMessage.message;
                    receivedMessage.setTimeMessageReturned((int) System.currentTimeMillis());
                    messages.add(receivedMessage);
                }
            } catch (IOException | ClassNotFoundException i) {
                throw new Error(i);
            }
        }
    }
}