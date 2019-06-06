package com.example.messenger;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messenger.system.Conversation;
import com.example.messenger.system.Keys;
import com.example.messenger.system.Message;

import java.util.ArrayList;
import java.util.Collections;

public class ChatWindow extends AppCompatActivity {

    private EditText et;
    private String messageText;
    private Conversation conversation;
    private ImageButton sendButton;
    private RecyclerView recyclerView;

    private String username;
    private ArrayList<String> participants = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private Global global;

    private NotificationCompat.Builder notification;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        notification = new NotificationCompat.Builder(ChatWindow.this, "2");
        notification.setAutoCancel(true);

        global = ((Global) this.getApplication());
        Intent i = getIntent();

        if(!global.getChatHandler().ch().hasConversations()) {
            // No conversation exists apparently
        } else {
            conversation = global.getChatHandler().ch().getConversation(i.getStringExtra("conversation"));
        }

        //Get the conversation from it's id.
        setContentView(R.layout.chat_window);
        sendButton = findViewById(R.id.sendButton);
        et = findViewById(R.id.messageField);
        recyclerView = findViewById(R.id.recycler_view);

        //get the participants
        conversation.update(global);
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

        fillArrays();
    }

    /**
     * Fills the messages array with the sorted messages from the conversation
     */
    public void fillArrays(){

        conversation.update(global);

        if(conversation.getSortedMessages().size() != 0) {
            for (Message message : conversation.getSortedMessages().values()) {
                messages.add(message);
            }
        }

        initRecyclerView();
    }

    private void initRecyclerView(){
        global.setAdapter(conversation);
        RecyclerViewAdapter adapter = global.getAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scrollToPosition(messages.size()-1);
    }

    public void sendMessage(View view){
        String messageText = et.getText().toString();

        //make a Message class from the message
        Message message = makeMessage(messageText);

        //actually send the message.
        //global.getChatHandler().sendMessage(message);

        messages.add(message);
        et.setText("");

        initRecyclerView();
        global.getChatHandler().sendMessage(message);
        //send message to server missing

        sendNotification(messages.get(messages.size()-1));
    }

    /**
     * Makes a Message class out of a string.
     * @param messageText The text of the message
     * @return Message
     */
    private Message makeMessage(String messageText) {
        return Message.makeMessage(messageText, conversation.getConversationId(), global);
    }

    //this method should be called if user is looking at particular chat and receives a message in a mean time
    public void receiveMessage(Message message){
        sendNotification(message);

        initRecyclerView();
    }

    public void sendNotification(Message last_message){

        //build the notification
        notification.setSmallIcon(R.drawable.icon_chat);
        notification.setTicker("You have a new message!");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(last_message.getSenderID());
        notification.setContentText(last_message.getMessage());


        //select what happens, when user clicks the notification
        Intent intent = new Intent(this, ChatWindow.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //"send" the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(4, notification.build());
    }
}
