package com.mohit.vidhidarpan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private Chatbot chatbot;
    private ProfileFragment profileFragment;
    private community community;
    private MessagesFragment messagesFragment;
    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        bottomNavigation = findViewById(R.id.bottomNavigation);
        // Initialize the fragments
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        chatbot = new Chatbot();
        community = new community();
        messagesFragment = new MessagesFragment();
        // Add navigation items
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_library_books_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_watch_later_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_work_outline_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_work_outline_24));

        // Set a listener for item clicks
        bottomNavigation.setOnClickMenuListener(model -> {
            // Handle navigation based on model.getId()
            switch (model.getId()) {
                case 1:
                    // Switch to the Home Fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, homeFragment)
                            .commit();
                    break;
                case 2:

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, chatbot)
                            .commit();
                    break;
                case 3:

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, community)
                            .commit();
                    break;
                    case 4:

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, messagesFragment)
                            .commit();
                    break;
                case 5:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, profileFragment)
                            .commit();
                    break;
            }
            return null;
        });

        // Set the default selected item
        bottomNavigation.show(1, true);
        // Add this to set HomeFragment as the initial fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, homeFragment)
                .commit();
    }


}

