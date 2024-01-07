package com.mohit.vidhidarpan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Retrieve user details from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("user_name", "");
        String email = sharedPreferences.getString("user_email", "");
        String phoneNumber = sharedPreferences.getString("user_phone_number", "");

        // Now, you can display these details in your TextViews in the view.
        TextView nameTextView = view.findViewById(R.id.textViewName);
        TextView emailTextView = view.findViewById(R.id.textViewEmail);
        TextView phoneNumberTextView = view.findViewById(R.id.textViewPhoneNumber);

        nameTextView.setText(name);
        emailTextView.setText(email);
        phoneNumberTextView.setText(phoneNumber);
        Button logout=view.findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Login_screen.class));
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("login_status", false);
                editor.apply();

            }
        });
        return view;
    }

}