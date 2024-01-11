package com.mohit.vidhidarpan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DescriptionActivity extends AppCompatActivity {
    private DatabaseReference chatRef;
    private EditText editTextMessage;
    private String currentUserKey;
    private String advocateFirebaseKey;
    private String senderName;
    private RatingBar ratingBar;
    private String senderEmail;
    private String senderNumber;
    private DatabaseReference databaseReference;
    private String receiverName = "Unknown Receiver";
    private String receiverEmail = "unknown@example.com";
    private String receiverNumber = "12345";
    private RecyclerView recyclerView;
    private FeedbackAdapter feedbackAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description); // Use the correct XML layout file

        // Retrieve data from the Intent
        Intent intent = getIntent();
        String advocateFirebaseKey = intent.getStringExtra("advocateFirebaseKey");
        String name = intent.getStringExtra("name");
        String phoneNumber = intent.getStringExtra("phonenumber");
        String description = intent.getStringExtra("discription");
        Button chat=findViewById(R.id.buttonNext);
        Button feedback=findViewById(R.id.submitFeedbackButton);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);


                intent.putExtra("advocateFirebaseKey",advocateFirebaseKey); // Pass Firebase key

                startActivity(intent);
            }
        });
        System.out.println("LLLLLLLLLLLLLLLLLL "+name+" "+phoneNumber+" "+description);

        // Display the data in your UI elements (e.g., TextViews)
        TextView advocateKeyTextView = findViewById(R.id.advocateKeyTextView);
        TextView nameTextView = findViewById(R.id.nameTextView);
        ratingBar = findViewById(R.id.ratingBar);
        TextView phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        editTextMessage = findViewById(R.id.feedbackEditText);
        advocateKeyTextView.setText(advocateFirebaseKey);
        nameTextView.setText(name);
        phoneNumberTextView.setText(phoneNumber);
        descriptionTextView.setText(description);

        fetchReceiverDetailsFromFirebase(advocateFirebaseKey);
        // Retrieve the current user's key from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        currentUserKey = sharedPreferences.getString("user_key", null);
        System.out.println("Keyyyyyyyyyyyyyyyyy "+advocateFirebaseKey+"|||||| "+currentUserKey);

        // Check if the user is logged in
        if (currentUserKey != null) {
            // Initialize Firebase Database reference for chat
            chatRef = FirebaseDatabase.getInstance().getReference("feedback")
                    .child(currentUserKey + "_" + advocateFirebaseKey); // Unique path for each chat

            feedback.setOnClickListener(v -> sendMessage());
        } else {
            // Redirect user to login screen or show a message
            // TODO: Handle the case where there is no logged-in user
        }
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewFeedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedbackAdapter = new FeedbackAdapter();
        recyclerView.setAdapter(feedbackAdapter);

        // Rest of your existing code...
        fetchFeedbackDataFromFirebase(advocateFirebaseKey);
        feedback.setOnClickListener(v -> sendMessage());

    }
    private void fetchFeedbackDataFromFirebase(final String advocateFirebaseKey) {
        DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("feedback");

        feedbackRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Feedback> feedbackList = new ArrayList<>();

                for (DataSnapshot outerSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot innerSnapshot : outerSnapshot.getChildren()) {
                        Feedback feedback = innerSnapshot.getValue(Feedback.class);
                        System.out.println("VALLLLLLLLLLLLL "+feedback.getFeedbackText());
                        if (feedback != null) {
                            feedbackList.add(feedback);
                        }
                    }
                }

                // Update the RecyclerView with all fetched feedback data
                feedbackAdapter.setFeedbackList(feedbackList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
                Log.e("FirebaseError", "Error fetching feedback data: " + databaseError.getMessage());
            }
        });
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
    private void fetchSenderDetailsFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        senderName = sharedPreferences.getString("user_name", "Unknown Sender");
        senderEmail = sharedPreferences.getString("user_email", "unknown@example.com");
        senderNumber=sharedPreferences.getString("user_phone_number", "123456");
    }

    private void sendMessage() {
        fetchSenderDetailsFromSharedPreferences();

        String messageText = editTextMessage.getText().toString().trim();
        float rating = ratingBar.getRating();
        if (!messageText.isEmpty()) {
            Feedback message = new Feedback(currentUserKey, senderName, senderEmail, receiverName, receiverEmail, rating,messageText, System.currentTimeMillis(),senderNumber,receiverNumber);
            chatRef.push().setValue(message);
            editTextMessage.setText("");
        }
    }
}
