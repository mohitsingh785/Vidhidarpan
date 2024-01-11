package com.mohit.vidhidarpan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login_screen extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private static final String LOGIN_STATUS_KEY = "login_status";
    private Button loginButton;
    private DatabaseReference usersRef;
    private DatabaseReference AdvocateRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
        AdvocateRef = database.getReference("advocates");

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
                loginAdvocate();
            }
        });
        TextView textView = (TextView) findViewById(R.id.textview_user);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_screen.this, User_register.class));

            }
        });
        TextView textView1 = (TextView) findViewById(R.id.textview_advocate_user);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_screen.this, advocate_register.class));

            }
        });
    }

    private void loginUser() {
        final String enteredEmail = emailEditText.getText().toString().trim();
        final String enteredPassword = passwordEditText.getText().toString();

        // Query the database to find the user with the provided email
        Query query = usersRef.orderByChild("email").equalTo(enteredEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userPassword = userSnapshot.child("password").getValue(String.class);
                        Log.d("LoginScreen", "Fetched Encrypted Password: " + userPassword);

                        if (AES.decrypt(userPassword).equals(enteredPassword)) {
                            // Successful login
                            setLoginStatus(true);
                            // Save user data in SharedPreferences
                            String userKey = userSnapshot.child(
                                    "firebaseKey").getValue(String.class); // Get the Firebase Database key
                            saveUserKeyInSharedPreferences(userKey);
                            String userName = userSnapshot.child("name").getValue(String.class);
                            String userPhoneNumber = userSnapshot.child("phoneNumber").getValue(String.class);

                            saveUserDataInSharedPreferences(userName, enteredEmail, userPhoneNumber);
                            startActivity(new Intent(Login_screen.this, MainActivity.class));
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    // Invalid login credentials
                    Toast.makeText(getApplicationContext(), "Invalid login credentials", Toast.LENGTH_SHORT).show();
                } else {
                    // User not found
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
                Toast.makeText(getApplicationContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loginAdvocate() {
        final String enteredEmail = emailEditText.getText().toString().trim();
        final String enteredPassword = passwordEditText.getText().toString();

        // Query the database to find the user with the provided email
        Query query = AdvocateRef.orderByChild("email").equalTo(enteredEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userPassword = userSnapshot.child("password").getValue(String.class);

                        if (AES.decrypt(userPassword).equals(enteredPassword)) {
                            // Successful login
                            setLoginStatus(true);
                            String userKey = userSnapshot.child(
                                    "firebaseKey").getValue(String.class); // Get the Firebase Database key
                            saveUserKeyInSharedPreferences(userKey);
                            String userName = userSnapshot.child("name").getValue(String.class);
                            String userPhoneNumber = userSnapshot.child("phoneNumber").getValue(String.class);

                            saveUserDataInSharedPreferences(userName, enteredEmail, userPhoneNumber);
                            startActivity(new Intent(Login_screen.this, MainActivity.class));
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    // Invalid login credentials
                    Toast.makeText(getApplicationContext(), "Invalid login credentials", Toast.LENGTH_SHORT).show();
                } else {
                    // User not found
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
                Toast.makeText(getApplicationContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setLoginStatus(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_STATUS_KEY, isLoggedIn);
        editor.apply();
    }
    private void saveUserDataInSharedPreferences(String name, String email, String phoneNumber) {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name", name);
        editor.putString("user_email", email);
        editor.putString("user_phone_number", phoneNumber);
        editor.apply();
    }
    private void saveUserKeyInSharedPreferences(String userKey) {
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_key", userKey);
        editor.apply();
    }
}