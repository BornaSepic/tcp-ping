package com.tcpping.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {
    public String message;
    public int timeMessageCreated;
    public int timeMessageArrived;
    public int timeMessageReturned;

    private String generateMessage(int messageSize) {
        StringBuilder sb = new StringBuilder(messageSize);
        for (int i = 0; i < messageSize; i++) {
            String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public Message(int messageSize) {
        message = generateMessage(messageSize);
        timeMessageCreated = (int) System.currentTimeMillis();
    }
}

