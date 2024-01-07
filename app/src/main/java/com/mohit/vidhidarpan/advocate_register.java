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

public class advocate_register extends AppCompatActivity {
    private DatabaseReference advocatesRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advocate_register);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        advocatesRef = database.getReference("advocates");

        // Assuming you have EditText fields for name, email, password, phoneNumber, barCouncilNumber, description, casesWon, and casesTaken
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        EditText barCouncilNumberEditText = findViewById(R.id.barCouncilNumberEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        EditText casesWonEditText = findViewById(R.id.casesWonEditText);
        EditText casesTakenEditText = findViewById(R.id.casesTakenEditText);

        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve advocate input from the EditText fields
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();
                String barCouncilNumber = barCouncilNumberEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                int casesWon = Integer.parseInt(casesWonEditText.getText().toString());
                int casesTaken = Integer.parseInt(casesTakenEditText.getText().toString());

                // Check if all fields are filled
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(barCouncilNumber)
                        || TextUtils.isEmpty(description) || casesWon == 0 || casesTaken == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // All fields are filled, proceed with advocate registration
                    password=AES.encrypt(password);
                    AdvocateModel newAdvocate = new AdvocateModel(name, email, phoneNumber, barCouncilNumber, description, casesWon, casesTaken, password);
                    registerAdvocate(newAdvocate);
                }
            }
        });
    }

    // Register the advocate
    private void registerAdvocate(AdvocateModel newAdvocate) {
        // Check if the email already exists
        advocatesRef.orderByChild("email").equalTo(newAdvocate.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Email already exists, display an error message
                    Toast.makeText(getApplicationContext(), "Advocate already exists", Toast.LENGTH_SHORT).show();
                } else {
                    // Email doesn't exist, proceed to create the new advocate
                    String advocateId = advocatesRef.push().getKey(); // Generate a unique key for the advocate
                    advocatesRef.child(advocateId).setValue(newAdvocate, new DatabaseReference.CompletionListener() {
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