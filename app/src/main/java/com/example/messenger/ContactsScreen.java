package com.example.messenger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactsScreen extends AppCompatActivity {

    private String[] contacts_array;
    private ListView list_view;
    private EditText search_bar;
    private ArrayList<String> contacts;
    private ArrayAdapter array_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_screen);

        list_view = findViewById(R.id.contactsListView);
        search_bar = findViewById(R.id.searchContactBar);

        InitListView();

        search_bar.addTextChangedListener(new TextWatcher() {
            int length = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length=s.toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")) {
                    InitListView();
                }
                else {
                    SearchContact(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < length) {
                    InitListView();
                    for (String item:contacts_array) {
                        if (!item.toLowerCase().contains(s.toString().toLowerCase())) {
                            contacts.remove(item);
                        }
                    }
                }
            }
        });
    }

    private void SearchContact(String text) {
        for(String c:contacts_array) {
            if (!c.toLowerCase().contains(text.toString().toLowerCase())) {
                contacts.remove(c);
            }
        }

        array_adapter.notifyDataSetChanged();
    }

    private void InitListView() {
        contacts_array = new String[]{"ba", "la", "ga"};
        contacts = new ArrayList<>(Arrays.asList(contacts_array));
        array_adapter = new ArrayAdapter(this, R.layout.searchlist_layout, R.id.searchlist_contact, contacts);
        list_view.setAdapter(array_adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
                SaveContact(contacts.get(i));
                finish();
            }
        });
    }

    private void SaveContact(String name) {
        StringBuilder string = new StringBuilder();
        SharedPreferences sp = getSharedPreferences("PrefsFile", MODE_PRIVATE);

        if(sp.contains("pref_contacts")) {
            string.append(sp.getString("pref_contacts", "")).append(name + ",");
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("pref_contacts", string.toString());
        editor.apply();
    }
}
