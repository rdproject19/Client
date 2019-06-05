package com.example.messenger;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.messenger.system.Message;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private String sender;
    private String receiver;
    private ArrayList<String> participants = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();

    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> participants, ArrayList<Message> messages, Context mContext) {
        this.participants = participants;
        this.sender = participants.get(0);
        this.receiver = participants.get(1);
        this.messages = messages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagelist_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Message message = messages.get(i);

        String id = message.getSenderID();

        if (sender.equals(id)) { //sent messages
            viewHolder.user.setText(sender);

            viewHolder.message.setText(message.getMessage());
            viewHolder.time.setText(message.getTimeString());

            viewHolder.user.setBackgroundResource(R.drawable.sender_top);
            viewHolder.message.setBackgroundResource(R.drawable.sender_middle);
            viewHolder.time.setBackgroundResource(R.drawable.sender_bottom);

            //margins
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(250, 0, 0, 0);

            viewHolder.user.setLayoutParams(params);
            viewHolder.message.setLayoutParams(params);
            viewHolder.time.setLayoutParams(params);

            viewHolder.time.setPadding(0, 0, 35, 0);

        } else { //recieved messages
            viewHolder.user.setText(receiver);

            viewHolder.message.setText(message.getMessage());
            viewHolder.time.setText(message.getTimeString());

            viewHolder.user.setBackgroundResource(R.drawable.receiver_top);
            viewHolder.message.setBackgroundResource(R.drawable.receiver_middle);
            viewHolder.time.setBackgroundResource(R.drawable.receiver_bottom);

            //margins
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 250, 0);

            viewHolder.user.setLayoutParams(params);
            viewHolder.message.setLayoutParams(params);
            viewHolder.time.setLayoutParams(params);

            viewHolder.time.setPadding(0, 0, 35, 0);

        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView user;
        TextView message;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.user);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);

        }
    }
}