package com.gucarsoft.socketbe.model;
public class Message<T> {
    private String username;
    private T message;
    private String messageType;

    // Getters và Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "username='" + username + '\'' +
                ", message=" + message +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}

