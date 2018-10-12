package com.workspaceapp.skamper.data.model;

public class Message {
    private long timeInMillis;
    private String body;
    private String recipient;
    private String sender;

    public Message(long timeInMillis, String body, String recipient, String sender) {
        this.timeInMillis = timeInMillis;
        this.body = body;
        this.recipient = recipient;
        this.sender = sender;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
