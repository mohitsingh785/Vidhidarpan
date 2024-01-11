package com.mohit.vidhidarpan;




import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder> {
    private final List<ChatMessage> messages;

    public ChatMessagesAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvMessage;
        private final TextView tvTimestamp;

        private final LinearLayout layout;

        MessageViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView; // Reference to the LinearLayout
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);

        }

        void bind(ChatMessage message) {
            tvMessage.setText(message.getMessage());
            tvTimestamp.setText(formatTimestamp(message.getTimestamp()));


            // Adjust the layout based on the sender
            if ("You".equals(message.getSenderLabel())) {
                layout.setGravity(Gravity.END);
                tvMessage.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.chat_bg_you));
                tvMessage.setTextColor(itemView.getContext().getResources().getColor(R.color.less_white));
                // Reorder views if necessary
            } else {
                layout.setGravity(Gravity.START);
                tvMessage.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.chat_bg));
                tvMessage.setTextColor(itemView.getContext().getResources().getColor(R.color.light_grey));
                // Reorder views if necessary
            }
        }

        private String formatTimestamp(long timestamp) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            return sdf.format(new Date(timestamp));
        }
    }


}

