package com.mohit.vidhidarpan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ConversationViewHolder> {
    private final List<ConversationSummary> conversationSummaries;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ConversationSummary conversationSummary);
    }

    public MessagesAdapter(List<ConversationSummary> conversationSummaries, OnItemClickListener onItemClickListener) {
        this.conversationSummaries = conversationSummaries;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation_summary, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder holder, int position) {
        ConversationSummary summary = conversationSummaries.get(position);
        holder.bind(summary, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return conversationSummaries.size();
    }

    static class ConversationViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSenderName;
        private final TextView tvLatestMessage;
        private final TextView tvTimestamp;

        ConversationViewHolder(View itemView) {
            super(itemView);
            tvSenderName = itemView.findViewById(R.id.tvSenderName);
            tvLatestMessage = itemView.findViewById(R.id.tvLatestMessage);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
        }

        void bind(ConversationSummary summary, OnItemClickListener onItemClickListener) {
            tvSenderName.setText(summary.getSenderName());
            tvLatestMessage.setText(summary.getLatestMessage());
            tvTimestamp.setText(formatTimestamp(summary.getTimestamp()));

            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(summary));
        }

        private String formatTimestamp(long timestamp) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            return sdf.format(new Date(timestamp));
        }
    }
}
