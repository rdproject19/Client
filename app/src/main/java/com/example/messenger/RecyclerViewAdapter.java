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

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    String user1, user2;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<Boolean> sent = new ArrayList<>();

    private Context mContext;

    public RecyclerViewAdapter(String user1, String user2, ArrayList<String> messages, ArrayList<String> time, ArrayList<Boolean> sent, Context mContext) {
        this.user1 = user1;
        this.user2 = user2;
        this.messages = messages;
        this.time = time;
        this.sent = sent;
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
        if (sent.get(i)) { //sent messages
            viewHolder.user.setText(user1);
            viewHolder.message.setText(messages.get(i));
            viewHolder.time.setText(time.get(i));

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
            viewHolder.user.setText(user2);
            viewHolder.message.setText(messages.get(i));
            viewHolder.time.setText(time.get(i));

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