package com.example.messenger;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.messenger.system.Conversation;
import com.example.messenger.system.Keys;
import com.example.messenger.system.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

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

    private DatePickerDialog.OnDateSetListener dateListener;
    private TimePickerDialog.OnTimeSetListener timeListener;
    private final String TAG = "ChatWindow";
    private int y;
    private int m;
    private int d;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

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

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = year;
                m = month;
                d = dayOfMonth;
                time();
            }
        };

        timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, y);
                cal.set(Calendar.MONTH, m);
                cal.set(Calendar.DAY_OF_MONTH, d);
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                Date date = cal.getTime();
                Long unix_time = new Long(date.getTime()/1000);
                sendMessage(unix_time);
            }
        };

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

    public void send(View view) {
        sendMessage((long)0);
    }

    public void sendMessage(Long time_message){
        String messageText = et.getText().toString();

        Message message = (time_message > 0) ? makeTimedMessage(messageText, time_message) : makeMessage(messageText);

        messages.add(message);
        et.setText("");

        initRecyclerView();
        global.getChatHandler().sendMessage(message);
    }

    /**
     * Makes a Message class out of a string.
     * @param messageText The text of the message
     * @return Message
     */
    private Message makeMessage(String messageText) {
        return Message.makeMessage(messageText, conversation.getConversationId(), global);
    }

    private Message makeTimedMessage(String messageText, Long time) {
        return Message.makeMessage(messageText, conversation.getConversationId(), time, global);
    }

    //this method should be called if user is looking at particular chat and receives a message in a mean time
    public void receiveMessage(){
        initRecyclerView();
    }

    public void date(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(ChatWindow.this, android.R.style.Theme_Black, dateListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void time() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(ChatWindow.this, android.R.style.Theme_Black, timeListener, hour, minute, true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
