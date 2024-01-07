package com.mohit.vidhidarpan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Use a handler to delay the transition to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the delay
                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                // The second argument is the default value in case the key is not found
                 boolean login=sharedPreferences.getBoolean("login_status", false);

                 if(login){
                     startActivity(new Intent(splash_screen.this, MainActivity.class));


                 }
                 else{
                     startActivity(new Intent(splash_screen.this, Login_screen.class));
                 }

                finish(); // Close the splash screen activity
            }
        }, 2000);
    }
}