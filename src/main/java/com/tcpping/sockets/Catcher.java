package com.tcpping.sockets;

import com.tcpping.models.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Catcher {

    public void Server(int port) throws IOException {
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;

        try {
            ServerSocket server = new ServerSocket(port);

            Socket socket = server.accept();

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            String message = "";

            while (!"OVER".equals(message)) {
                try {
                    Message receivedMessage = (Message) objectInputStream.readObject();
                    message = receivedMessage.message;

                    receivedMessage.setTimeMessageArrived((int) System.currentTimeMillis());

                    objectOutputStream.writeObject(receivedMessage);

                } catch (IOException i) {
                    throw new IOException(i);
                }
            }
        } catch (IOException | ClassNotFoundException i) {
            throw new IOException(i);
        }
    }
}