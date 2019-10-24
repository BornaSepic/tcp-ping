package com.tcpping.generator;

public class MessageGenerator {
    public String generateMessage(int messageSize) {
        StringBuilder sb = new StringBuilder(messageSize);

        for (int i = 0; i < messageSize; i++) {
            sb.append('a');
        }

        return sb.toString();
    }
}
