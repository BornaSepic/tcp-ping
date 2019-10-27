package com.tcpping.sockets;

import com.tcpping.models.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Catcher {

    public void Server(int port) {
        try (ServerSocket server = new ServerSocket(port);
             Socket socket = server.accept();
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

            String message = "";

            while (!"OVER".equals(message)) {
                try {
                    Message receivedMessage = (Message) objectInputStream.readObject();
                    message = receivedMessage.message;

                    receivedMessage.setTimeMessageArrived((int) System.currentTimeMillis());

                    objectOutputStream.writeObject(receivedMessage);

                } catch (IOException i) {
                    throw new RuntimeException("Exception while reading from socket...");
                }
            }
        } catch (IOException | ClassNotFoundException i) {
            throw new Error(i);
        }
    }
}