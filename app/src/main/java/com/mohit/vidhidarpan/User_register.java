package com.mohit.vidhidarpan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_register extends AppCompatActivity {
    private DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
        // Assuming you have EditText fields for name, email, password, and phoneNumber
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText phoneNumberEditText = findViewById(R.id.phoneNumberEditText);

        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user input from the EditText fields
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();

                // Check if all fields are filled
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // All fields are filled, proceed with registration
                    password=AES.encrypt(password);
                    userModel newUser = new userModel(name, email, password, phoneNumber);
                    registerUser(newUser);
                }
            }
        });
    }
// Register the user
        private void registerUser(userModel newUser) {
            // Check if the email already exists
            usersRef.orderByChild("email").equalTo(newUser.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Email already exists, display an error message
                        Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        // Email doesn't exist, proceed to create the new user
                        String userId = usersRef.push().getKey(); // Generate a unique key for the user
                        newUser.setFirebaseKey(userId);
                        usersRef.child(userId).setValue(newUser, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    // Data could not be saved
                                    Toast.makeText(getApplicationContext(), "Data could not be saved: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    // Data saved successfully
                                    Toast.makeText(getApplicationContext(), "Data saved successfully.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any errors that may occur
                    Toast.makeText(getApplicationContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }