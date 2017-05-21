package com.example.lekham.appchat.models;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class Chat {
    private String sender;
    private String receiver;
    private String senderUid;
    private String receiverUid;
    private String message;
    private long timestamp;

    public Chat() {

    }

    public Chat(String sender, String receiver, String senderUid, String receiverUid, String message, long timestamp) {
        this.setSender(sender);
        this.setReceiver(receiver);
        this.setSenderUid(senderUid);
        this.setReceiverUid(receiverUid);
        this.setMessage(message);
        this.setTimestamp(timestamp);
    }

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

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
