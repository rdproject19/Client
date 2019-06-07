package com.example.messenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.messenger.system.Contact;
import com.example.messenger.system.ContactDao;
import com.example.messenger.system.Conversation;
import com.example.messenger.system.ConversationDao;
import com.example.messenger.system.Keys;
import com.example.messenger.system.http.User;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactResult extends AppCompatActivity {

    private TextView fullname;
    private TextView username;
    private Button resultbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_resultscreen);
        resultbtn = findViewById(R.id.Resultbutton);
        fullname = findViewById(R.id.res_fullname);
        username = findViewById(R.id.res_username);
        Intent a = getIntent();
        fullname.setText(a.getStringExtra("fullname"));
        username.setText(a.getStringExtra("username"));
        ((Global) this.getApplication()).stopAlarm();
    }

    public void save(View view) {
        SaveContact(username.getText().toString(), fullname.getText().toString());
        finish();
    }

    private void SaveContact(String username, String fullname) {
        StringBuilder string = new StringBuilder();
        try {
            User.addUserContact(((Global) this.getApplication()).getUserData().getString(Keys.USERNAME), username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Contact contact = new Contact(username, fullname);
        ContactDao convDao = ((Global) this.getApplication()).db().contactDao();
        convDao.putContact(contact);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((Global) this.getApplication()).startAlarm();
    }
}
