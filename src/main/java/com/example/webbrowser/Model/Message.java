package com.example.webbrowser.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime timestamp;

    // Default constructor
    public Message() {
        // Initialize timestamp with the current time
        this.timestamp = LocalDateTime.now();
    }

    // Parameterized constructor with sender and content
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        // Initialize timestamp with the current time
        this.timestamp = LocalDateTime.now();
    }

    // Parameterized constructor with sender, receiver, and content
    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        // Initialize timestamp with the current time
        this.timestamp = LocalDateTime.now();
    }

    // getters and setters...

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
