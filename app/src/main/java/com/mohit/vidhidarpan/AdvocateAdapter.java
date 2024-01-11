package com.mohit.vidhidarpan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class AdvocateAdapter extends RecyclerView.Adapter<AdvocateAdapter.AdvocateViewHolder> {
    private static List<AdvocateModel> advocateList;

    public AdvocateAdapter(List<AdvocateModel> advocateList) {
        this.advocateList = advocateList;
    }

    @NonNull
    @Override
    public AdvocateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advocate_item, parent, false);
        return new AdvocateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvocateViewHolder holder, int position) {
        AdvocateModel advocate = advocateList.get(position);

        holder.advocateName.setText(advocate.getName());
        holder.advocatePhoneNumber.setText(advocate.getPhoneNumber());
        holder.advocateDescription.setText(advocate.getDescription());

        // Calculate and set the win percentage
        double winPercentage = (double) advocate.getCasesWon() / advocate.getCasesTaken() * 100;
        holder.advocateWinPercentage.setText(String.format(Locale.getDefault(), "%.2f%%", winPercentage));
    }

    @Override
    public int getItemCount() {
        return advocateList.size();
    }

    static class AdvocateViewHolder extends RecyclerView.ViewHolder {
        TextView advocateName;
        TextView advocatePhoneNumber;
        TextView advocateDescription;
        TextView advocateWinPercentage;

        public AdvocateViewHolder(@NonNull View itemView) {
            super(itemView);
            advocateName = itemView.findViewById(R.id.advocateName);
            advocatePhoneNumber = itemView.findViewById(R.id.advocatePhoneNumber);
            advocateDescription = itemView.findViewById(R.id.advocateDescription);
            advocateWinPercentage = itemView.findViewById(R.id.advocateWinPercentage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        AdvocateModel advocate = advocateList.get(position);
                        Intent intent = new Intent(v.getContext(), DescriptionActivity.class);
                        System.out.println("LLLLLLLLLLLLLLLLLL "+advocate.getName()+" "+advocate.getPhoneNumber()+" "+advocate.getDescription());

                        intent.putExtra("advocateFirebaseKey", advocate.getFirebaseKey()); // Pass Firebase key
                        intent.putExtra("name",  advocate.getName()); // Pass Firebase key
                        intent.putExtra("phonenumber", advocate.getPhoneNumber()); // Pass Firebase key
                        intent.putExtra("discription", advocate.getDescription()); // Pass Firebase key
                        v.getContext().startActivity(intent);
                    }
                }
            });

        }
    }
}

