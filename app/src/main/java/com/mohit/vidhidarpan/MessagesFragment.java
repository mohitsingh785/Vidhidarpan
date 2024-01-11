package com.mohit.vidhidarpan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessagesFragment extends Fragment implements MessagesAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private List<ConversationSummary> conversationSummaries = new ArrayList<>();

    private String currentUserKey;

    @Override
    public void onItemClick(ConversationSummary conversationSummary) {
        Intent intent = new Intent(getActivity(), ConversationDetailActivity.class);
        intent.putExtra("senderId", conversationSummary.getSenderId());
        // You can pass additional data if needed
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        recyclerView = view.findViewById(R.id.messagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        currentUserKey = sharedPreferences.getString("user_key", null);

        adapter = new MessagesAdapter(conversationSummaries,this);

        recyclerView.setAdapter(adapter);

        loadMessages();
        System.out.println("thisssssssssssss");
        return view;
    }

    private void loadMessages() {
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("chats");

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversationSummaries.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String chatId = snapshot.getKey();
                    if (chatId != null && chatId.endsWith("_" + currentUserKey)) {
                        for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                            ChatMessage message = messageSnapshot.getValue(ChatMessage.class);
                            if (message != null) {
                                ConversationSummary summary = new ConversationSummary(
                                        message.getSenderId(),
                                        message.getSenderName(),
                                        message.getMessage(),
                                        message.getTimestamp()
                                );
                                // You'll need logic here to ensure only the latest message per sender is added
                                conversationSummaries.add(summary);
                            }
                        }
                    }
                }
                // Logic to keep only the latest message per sender
                filterForLatestMessages();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }
    private void filterForLatestMessages() {
        Map<String, ConversationSummary> latestMessagesMap = new HashMap<>();

        for (ConversationSummary summary : conversationSummaries) {
            String senderId = summary.getSenderId();
            ConversationSummary existingSummary = latestMessagesMap.get(senderId);

            if (existingSummary == null || existingSummary.getTimestamp() < summary.getTimestamp()) {
                latestMessagesMap.put(senderId, summary);
            }
        }

        conversationSummaries.clear();
        conversationSummaries.addAll(latestMessagesMap.values());
    }




}
