package com.tcpping.sockets;

import com.tcpping.models.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Catcher {

    public void Server(int port) {
        try {
            ServerSocket server = new ServerSocket(port);

            Socket socket = server.accept();

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String message = "";

            while (!message.equals("OVER")) {
                try {
                    Message recievedMessage = (Message) objectInputStream.readObject();
                    message = recievedMessage.message;

                    System.out.println(recievedMessage.message);
                    System.out.println(recievedMessage.timeMessageCreated);
                } catch (IOException i) {
                    throw new Error(i);
                }
            }
        } catch (IOException | ClassNotFoundException i) {
            throw new Error(i);
        }
    }
}