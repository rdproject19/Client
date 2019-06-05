package com.example.messenger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.example.messenger.system.AppDatabase;
import com.example.messenger.system.ChatHandler;
import com.example.messenger.system.Conversation;
import com.example.messenger.system.ConversationDao;
import com.example.messenger.system.Keys;
import com.example.messenger.system.UserData;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AddChatScreen extends AppCompatActivity {

    private ListView add_chat_list;
    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global = ((Global) this.getApplication());

        setContentView(R.layout.addchat_screen);
        add_chat_list = findViewById(R.id.addchatlist);
        add_chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
                SaveChat(i);
                finish();
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                add_chat_list.setAdapter(MessengerScreen.contact_adapter);
            }
        }, 500);
    }

    private void SaveChat(int number) {
        StringBuilder string = new StringBuilder();
        SharedPreferences sp = getSharedPreferences("PrefsFile", MODE_PRIVATE);

        String contact = sp.getString("pref_contacts", "");
        String[] savedcontacts = contact.split(",");

        /*
        if(sp.contains("pref_chats")) {
            string.append(sp.getString("pref_chats", "")).append(savedcontacts[number] + ",");
        }
        else {
            string.append(savedcontacts[number] + ",");
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("pref_chats", string.toString());
        editor.apply();*/

        ConversationDao cd = ((Global) this.getApplication()).db().conversationDao();
        UserData ud = ((Global) this.getApplication()).getUserData();
        ChatHandler ch = ((Global) this.getApplication()).getChatHandler();

        Conversation c = new Conversation(new Random().nextInt(5000), ((Global) this.getApplication()));
        c.addParticipant(ud.getString(Keys.USERNAME));
        c.addParticipant(savedcontacts[number]);
        ch.ch().putConversation(c);
        cd.putConversation(c);
    }
}
