package com.mohit.vidhidarpan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private List<Feedback> feedbackList;

    public FeedbackAdapter() {
        feedbackList = new ArrayList<>();
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_item, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);
        holder.senderNameTextView.setText(feedback.getSenderName());
        holder.senderEmailTextView.setText(feedback.getSenderEmail());
        holder.ratingBar.setRating(feedback.getFeedbackRating());
        holder.feedbackTextView.setText(feedback.getFeedbackText());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView senderNameTextView;
        TextView senderEmailTextView;
        RatingBar ratingBar;
        TextView feedbackTextView;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            senderNameTextView = itemView.findViewById(R.id.senderNameTextView);
            senderEmailTextView = itemView.findViewById(R.id.senderEmailTextView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            feedbackTextView = itemView.findViewById(R.id.feedbackTextView);
        }
    }
}
