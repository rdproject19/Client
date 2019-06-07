package com.example.messenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.messenger.system.Contact;
import com.example.messenger.system.ContactDao;
import com.example.messenger.system.Conversation;
import com.example.messenger.system.ConversationDao;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactResult extends AppCompatActivity {

    private ArrayAdapter array_adapter;
    private ArrayList contacts;
    private String [] contacts_array = new String [1];
    ListView contact_result_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_screen);
        contact_result_list = findViewById(R.id.contact_result_list);
        Intent j =getIntent();
        InitListView(j.getStringExtra("Result list"));


    }

    private void InitListView(String result) {

        contacts_array[0] = "bava";
        array_adapter = new ArrayAdapter (getBaseContext(), R.layout.searchlist_layout, R.id.searchlist_contact, contacts_array);
        contact_result_list.setAdapter(array_adapter);
        contact_result_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
                SaveContact(contacts.get(i).toString(), "");
                finish();
            }
        });
    }

    private void SaveContact(String username, String fullname) {
        StringBuilder string = new StringBuilder();
        Contact contact = new Contact(username, fullname);
        ContactDao convDao = ((Global) this.getApplication()).db().contactDao();
        convDao.putContact(contact);
    }


}
