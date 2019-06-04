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

    String username;
    ArrayList<Message> messages = new ArrayList<>();

    private Context mContext;

    public RecyclerViewAdapter(String username, ArrayList<Message> messages, Context mContext) {
        this.username = username;
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

        if (username == message.getSenderID()) { //sent messages
            viewHolder.user.setText(username);

            viewHolder.message.setText(message.getMessage());
            viewHolder.time.setText(message.getTimeString());

            viewHolder.user.setBackgroundColor(Color.GRAY);
            viewHolder.message.setBackgroundColor(Color.GRAY);
            viewHolder.time.setBackgroundColor(Color.GRAY);

            //margins
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(250, 0, 0, 0);
            viewHolder.user.setLayoutParams(params);
            viewHolder.message.setLayoutParams(params);
            viewHolder.time.setLayoutParams(params);

        } else { //recieved messages
            viewHolder.user.setText("Other Guy");

            viewHolder.message.setText(message.getMessage());
            viewHolder.time.setText(message.getTimeString());

            viewHolder.user.setBackgroundColor(Color.GREEN);
            viewHolder.message.setBackgroundColor(Color.GREEN);
            viewHolder.time.setBackgroundColor(Color.GREEN);

            //margins
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 250, 0);
            viewHolder.user.setLayoutParams(params);
            viewHolder.message.setLayoutParams(params);
            viewHolder.time.setLayoutParams(params);

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