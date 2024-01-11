package com.mohit.vidhidarpan;

public class ConversationSummary {
    private String senderId;
    private String senderName;
    private String latestMessage;
    private long timestamp;

    public ConversationSummary() {
        // Default constructor required for calls to DataSnapshot.getValue(ConversationSummary.class)
    }

    public ConversationSummary(String senderId, String senderName, String latestMessage, long timestamp) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.latestMessage = latestMessage;
        this.timestamp = timestamp;
    }

    // Getters
    public String getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
