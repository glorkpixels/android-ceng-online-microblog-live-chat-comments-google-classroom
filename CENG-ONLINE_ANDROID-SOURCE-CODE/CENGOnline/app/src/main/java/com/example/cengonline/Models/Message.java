package com.example.cengonline.Models;

import com.google.firebase.database.ServerValue;

public class Message {
// message creadential who sent whom to sent and a timestamp with message that sent
    private String receiver,sender,message;
    private Object timestamp;

    public Message(String receiver, String sender, String message) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Message(){

    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
