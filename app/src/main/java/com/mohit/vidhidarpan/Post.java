package com.mohit.vidhidarpan;

public class Post {
    private String title;
    private String content;

    public Post() {
        // Empty constructor needed for Firestore deserialization
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

