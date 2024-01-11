package com.mohit.vidhidarpan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
// ... other imports ...

public class ChatActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_chat);

        editTextMessage = findViewById(R.id.editTextMessage);
        Button sendButton = findViewById(R.id.buttonSend);

        // Get the advocate's Firebase key from the Intent
        advocateFirebaseKey = getIntent().getStringExtra("advocateFirebaseKey");
        fetchReceiverDetailsFromFirebase(advocateFirebaseKey);
        // Retrieve the current user's key from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        currentUserKey = sharedPreferences.getString("user_key", null);
        System.out.println("Keyyyyyyyyyyyyyyyyy "+advocateFirebaseKey+"|||||| "+currentUserKey);

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
    private void fetchSenderDetailsFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        senderName = sharedPreferences.getString("user_name", "Unknown Sender");
        senderEmail = sharedPreferences.getString("user_email", "unknown@example.com");
        senderNumber=sharedPreferences.getString("user_phone_number", "123456");
    }
    private void fetchReceiverDetailsFromFirebase(String receiverKey) {
        databaseReference = FirebaseDatabase.getInstance().getReference("advocates").child(receiverKey);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    receiverName = dataSnapshot.child("name").getValue(String.class);
                    receiverEmail = dataSnapshot.child("email").getValue(String.class);
                    receiverNumber= dataSnapshot.child("phoneNumber").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
    private void sendMessage() {
        fetchSenderDetailsFromSharedPreferences();

        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            ChatMessage message = new ChatMessage(currentUserKey, senderName, senderEmail, receiverName, receiverEmail, messageText, System.currentTimeMillis(),senderNumber,receiverNumber);
            chatRef.push().setValue(message);
            editTextMessage.setText("");
        }
    }


    // Add methods to listen for messages and update UI
    // ...
}
