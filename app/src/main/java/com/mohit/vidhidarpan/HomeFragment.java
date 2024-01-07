package com.mohit.vidhidarpan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private DatabaseReference advocatesRef;
    private RecyclerView recyclerView;
    private AdvocateAdapter adapter;
    private List<AdvocateModel> advocateList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        advocatesRef = database.getReference("advocates");

        recyclerView = view.findViewById(R.id.recyclerView); // Fix: Use view.findViewById
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext())); // Use requireContext()
        advocateList = new ArrayList<>();
        System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmffffffffffffmmmmmmmmmmmmmmmmmmmm");

        adapter = new AdvocateAdapter(advocateList);
        recyclerView.setAdapter(adapter);

        fetchAdvocateData();
        adapter.notifyDataSetChanged();
        return view;
    }

    private void fetchAdvocateData() {
        advocatesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                advocateList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AdvocateModel advocate = snapshot.getValue(AdvocateModel.class);
                    if (advocate != null) {
                        advocateList.add(advocate);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
                Toast.makeText(requireContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
