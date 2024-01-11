package com.mohit.vidhidarpan;

public class ChatMessage {
    private String senderId;
    private String senderName;
    private String senderEmail;
    private String senderNumber;
    private String receiverName;
    private String receiverEmail;
    private String receiverNumber;
    private String message;
    private String SenderLabel;

    public String getSenderLabel() {
        return SenderLabel;
    }

    public void setSenderLabel(String senderLabel) {
        SenderLabel = senderLabel;
    }

    private long timestamp;

    public ChatMessage() { }

    public ChatMessage(String senderId, String senderName, String senderEmail, String receiverName, String receiverEmail, String message, long timestamp,String senderNumber,String receiverNumber) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.receiverName = receiverName;
        this.receiverEmail = receiverEmail;
        this.message = message;
        this.timestamp = timestamp;
        this.senderNumber=senderNumber;
        this.receiverNumber=receiverNumber;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
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
// Getters and setters for all fields
}
