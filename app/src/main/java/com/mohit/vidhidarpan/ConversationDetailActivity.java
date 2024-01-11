package com.mohit.vidhidarpan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConversationDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatMessagesAdapter adapter; // Modify this adapter to suit your message display
    private List<ChatMessage> chatMessages = new ArrayList<>();
    private String senderId;
    private DatabaseReference chatRef;
    private EditText editTextMessage;
    private String currentUserKey;
    private String advocateFirebaseKey;
    private String senderName;
    private String senderEmail;
    private String senderNumber;
    private DatabaseReference databaseReference;
    private String receiverName = "Unknown Receiver";
    private String receiverEmail = "unknown@example.com";
    private String receiverNumber = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_detail);

        recyclerView = findViewById(R.id.messagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editTextMessage = findViewById(R.id.messageEditText);
        Button sendButton = findViewById(R.id.sendButton);
        adapter = new ChatMessagesAdapter(chatMessages); // Assume this adapter is set up to display ChatMessage items
        recyclerView.setAdapter(adapter);
        TextView name=findViewById(R.id.tvName);
        TextView number=findViewById(R.id.tvPhoneNumber);
        TextView email=findViewById(R.id.tvEmail);
        // Retrieve the senderId from the intent
        Intent intent = getIntent();
        advocateFirebaseKey = intent.getStringExtra("senderId");
        fetchSenderDetailsFromSharedPreferences();
        fetchReceiverDetailsFromFirebase(advocateFirebaseKey);
        Log.d("FirebaseData", "Receiver Name: " + receiverName);
        Log.d("FirebaseData", "Receiver Email: " + receiverEmail);
        Log.d("FirebaseData", "Receiver Phone Number: " + receiverNumber);


        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        currentUserKey = sharedPreferences.getString("user_key", null);
        loadMessages(advocateFirebaseKey);
        // Check if the user is logged in
        if (currentUserKey != null) {
            // Initialize Firebase Database reference for chat
            chatRef = FirebaseDatabase.getInstance().getReference("chats")
                    .child(currentUserKey + "_" + advocateFirebaseKey); // Unique path for each chat

            sendButton.setOnClickListener(v -> sendMessage());
        } else {
            // Redirect user to login screen or show a message
            // TODO: Handle the case where there is no logged-in user
        }
    }

    private void loadMessages(String advocateFirebaseKey) {
        DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("chats");
        List<ChatMessage> allMessages = new ArrayList<>();

        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage message = snapshot.getValue(ChatMessage.class);
                    if (message != null) {
                        // Label the message based on the sender
                        if (message.getSenderId().equals(currentUserKey)) {
                            message.setSenderLabel("You");
                        } else {
                            message.setSenderLabel(receiverName);
                        }
                        allMessages.add(message);
                    }
                }
                // After fetching messages from both keys, sort and update UI
                sortAndDisplayMessages(allMessages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        };

        // Fetch messages from both possible chat keys
        chatsRef.child(advocateFirebaseKey + "_" + currentUserKey).addListenerForSingleValueEvent(messageListener);
        chatsRef.child(currentUserKey + "_" + advocateFirebaseKey).addListenerForSingleValueEvent(messageListener);
    }


    private void sortAndDisplayMessages(List<ChatMessage> allMessages) {
        // Sort messages by timestamp
        Collections.sort(allMessages, (msg1, msg2) -> Long.compare(msg1.getTimestamp(), msg2.getTimestamp()));

        // Clear existing messages and add sorted ones
        chatMessages.clear();
        chatMessages.addAll(allMessages);
        adapter.notifyDataSetChanged();
    }





    private void fetchSenderDetailsFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        senderName = sharedPreferences.getString("user_name", "Unknown Sender");
        senderEmail = sharedPreferences.getString("user_email", "unknown@example.com");
        senderNumber=sharedPreferences.getString("user_phone_number", "123456");
    }

    private void fetchReceiverDetailsFromFirebase(String receiverKey) {
        DatabaseReference advocatesRef = FirebaseDatabase.getInstance().getReference("advocates").child(receiverKey);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(receiverKey);
        ValueEventListener receiverDetailsListener = new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    receiverName = dataSnapshot.child("name").getValue(String.class);
                    receiverEmail = dataSnapshot.child("email").getValue(String.class);
                    receiverNumber = dataSnapshot.child("phoneNumber").getValue(String.class);

                    // Update UI here
                    TextView name = findViewById(R.id.tvName);
                    TextView number = findViewById(R.id.tvPhoneNumber);
                    TextView email = findViewById(R.id.tvEmail);

                    name.setText("Sender Name: " + receiverName);
                    email.setText("Sender Email: " + receiverEmail);
                    number.setText("Sender Number: " + receiverNumber);
                }
                else {
                    // If not found in advocates, check in users
                    usersRef.addListenerForSingleValueEvent(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        };
        advocatesRef.addListenerForSingleValueEvent(receiverDetailsListener);
    }


    private void sendMessage() {
        fetchSenderDetailsFromSharedPreferences();

        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            ChatMessage message = new ChatMessage(currentUserKey, senderName, senderEmail, receiverName, receiverEmail, messageText, System.currentTimeMillis(), senderNumber, receiverNumber);
            chatRef.push().setValue(message);
            editTextMessage.setText(""); // Clear the input field
        }
    }

}
