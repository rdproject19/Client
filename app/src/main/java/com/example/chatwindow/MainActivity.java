package com.example.chatwindow;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chatwindow.R;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    EditText et;
    String message;
    Button sendButton;

    String user1, user2;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();
    ArrayList<Boolean> sent = new ArrayList<>();

    public static final String notificationChanelID = "channel1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window);

        sendButton = findViewById(R.id.sendButton);
        et = findViewById(R.id.messageField);

        //get the users
        user1 = "user1";
        user2 = "user2";

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //enabling and disabling the send button
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                message = et.getText().toString();
                if(message.isEmpty()) {
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
        startAlarm();
    }

    public void fillArrays(){
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
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(user1, user2, messages, times, sent, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scrollToPosition(messages.size()-1);
    }

    public void sendMessage(View view){
        message = et.getText().toString();
        messages.add(message);
        times.add("20:20");
        sent.add(true);
        et.setText("");

        initRecyclerView();
    }

    public void startAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 5000, 60000, pendingIntent);
    }

    //this method should be called if user is looking at particular chat and receives a message in a mean time
    public void receiveMessage(String message, String time){
        messages.add(message);
        times.add(time);
        sent.add(false);

        initRecyclerView();
    }
}
