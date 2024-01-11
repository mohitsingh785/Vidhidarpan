package com.mohit.vidhidarpan;

public class Feedback {
    private String senderId;
    private String senderName;
    private String senderEmail;
    private String senderNumber;
    private String receiverName;
    private String receiverEmail;
    private String receiverNumber;
    private float feedbackRating; // Store the rating as a string
    private String feedbackText;   // Store the feedback text as a string
    private long timestamp;

    public Feedback() {
        // Default constructor required for Firebase Realtime Database
    }

    public Feedback(String senderId, String senderName, String senderEmail, String receiverName, String receiverEmail, float feedbackRating, String feedbackText, long timestamp, String senderNumber, String receiverNumber) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.receiverName = receiverName;
        this.receiverEmail = receiverEmail;
        this.feedbackRating = feedbackRating;
        this.feedbackText = feedbackText;
        this.timestamp = timestamp;
        this.senderNumber = senderNumber;
        this.receiverNumber = receiverNumber;
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

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getReceiverName() {
        return receiverName;
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

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public float getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(float feedbackRating) {
        this.feedbackRating = feedbackRating;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
// Getters and setters for all fields
    // ...
}
