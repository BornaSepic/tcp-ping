package com.tcpping.sockets;

import com.tcpping.generator.MessageGenerator;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Pitcher {
    private DataOutputStream out;

    private MessageGenerator messageGenerator = new MessageGenerator();

    private void sendMessages() throws IOException {
        String message = messageGenerator.generateMessage(500);
        out.writeUTF(message);
    }

    public void Client(String ip, int port) {


        Socket socket;
        BufferedReader input;
        try {
            socket = new Socket(ip, port);
            input = new BufferedReader(new InputStreamReader(System.in));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException u) {
            throw new Error(u);
        }

        String line = "";

        while (!line.equals("Over")) {
            try {
                sendMessages();
            } catch (IOException i) {
                throw new Error(i);
            }
        }

        try {
            input.close();
            out.close();
            socket.close();
        }
        catch (IOException i) {
            throw new Error(i);
        }
    }
}