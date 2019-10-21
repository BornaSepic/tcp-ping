package com.tcpping.sockets;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Catcher {

    public void Server(int port) {
        try {
            ServerSocket server = new ServerSocket(port);

            Socket socket = server.accept();

            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String message = "";

            while (!message.equals("Over")) {
                try {
                    message = in.readUTF();
                    System.out.println(message);
                } catch (IOException i) {
                    throw new Error(i);
                }
            }

            socket.close();
            in.close();
        } catch (IOException i) {
            throw new Error(i);
        }
    }
}