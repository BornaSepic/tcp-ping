package com.tcpping.models;

import java.io.Serializable;

public class Message implements Serializable {
    public int messageGroupId;
    private int id;
    public String message;

    private int timeMessageCreated;
    private int timeMessageArrived;
    private int timeMessageReturned;

    public int tripTimeToServer;
    public int tripTimeFromServer;
    public int totalTripTime;

    private String generateMessage(int messageSize) {
        StringBuilder sb = new StringBuilder(messageSize);
        for (int i = 0; i < messageSize; i++) {
            String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    private void setTimeMessageCreated(int timeMessageCreated) {
        this.timeMessageCreated = timeMessageCreated;
    }

    public void setTimeMessageArrived(int timeMessageArrived) {
        this.timeMessageArrived = timeMessageArrived;
        this.setTripTimeToServer();
    }

    public void setTimeMessageReturned(int timeMessageReturned) {
        this.timeMessageReturned = timeMessageReturned;
        this.setTripTimeFromServer();
    }

    private void setTripTimeToServer() {
        this.tripTimeToServer = this.timeMessageArrived - this.timeMessageCreated;
    }

    private void setTripTimeFromServer() {
        this.tripTimeFromServer = this.timeMessageReturned - this.timeMessageArrived;
        setTotalTripTime();
    }

    private void setTotalTripTime() {
        this.totalTripTime = this.tripTimeFromServer + this.tripTimeToServer;
    }

    public int getMessageGroupId() {
        return this.messageGroupId;
    }

    public Message(int messageSize, int messageId, int messageGroup) {
        messageGroupId = messageGroup;
        id = messageId;
        message = generateMessage(messageSize);
        setTimeMessageCreated((int) System.currentTimeMillis());
    }
}

