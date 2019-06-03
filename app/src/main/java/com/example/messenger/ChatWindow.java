package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.messenger.system.Conversation;
import com.example.messenger.system.Keys;
import com.example.messenger.system.Message;

import java.util.ArrayList;
import java.util.Collections;

public class ChatWindow extends AppCompatActivity {

    EditText et;
    String messageText;
    Conversation conversation;
    Button sendButton;

    String username;
    ArrayList<String> participants;
    ArrayList<Message> messages = new ArrayList<>();
    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        global = ((Global) this.getApplication());
        Intent i = getIntent();

        //Get the conversation from it's id.
        conversation = global.getChatHandler().ch().getConversation(Integer.parseInt(i.getStringExtra("convId")));

        setContentView(R.layout.chat_window);


        sendButton = findViewById(R.id.sendButton);
        et = findViewById(R.id.messageField);

        //get the participants
        username = global.getUserData().getString(Keys.FULLNAME);
        participants = conversation.getParticipants();

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //enabling and disabling the send button
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                messageText = et.getText().toString();
                if(messageText.isEmpty()) {
                    sendButton.setEnabled(false);
                } else {
                    sendButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //fillArrays();
    }

    /*public void fillArrays(){
        //real case - get the messages from shared preferences

        messages.add("Heey!");
        times.add("10:15");
        sent.add(true);

        messages.add("Hello");
        times.add("10:16");
        sent.add(false);

        messages.add("How are you?");
        times.add("10:16");
        sent.add(false);

        messages.add("blabla?");
        times.add("10:16");
        sent.add(true);

        messages.add("How are you?");
        times.add("10:16");
        sent.add(true);

        messages.add("fine?");
        times.add("10:16");
        sent.add(false);

        messages.add("fine?");
        times.add("10:16");
        sent.add(false);


        initRecyclerView();
    }*/

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(username, messages, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scrollToPosition(messages.size()-1);
    }

    public void sendMessage(View view){

        String messageText = et.getText().toString();
        Message message = Message.makeMessage(messageText, conversation.getID(), global);

        global.getChatHandler().sendMessage(message);

        messages.add(message);
        et.setText("");

        initRecyclerView();

        //send message to server missing
    }

    //this method should be called if user is looking at particular chat and receives a message in a mean time
    public void receiveMessage(Message message){
        messages.add(message);

        initRecyclerView();
    }



}
