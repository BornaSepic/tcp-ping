package com.tcpping.sockets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Pitcher {

    public void Client(String ip, int port) {
        Socket socket;
        BufferedReader input;
        DataOutputStream out;
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
                line = input.readLine();
                out.writeUTF(line);
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