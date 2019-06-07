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

import java.util.ArrayList;
import java.util.Arrays;

public class ContactResult extends AppCompatActivity {

    private ArrayAdapter array_adapter;
    private ArrayList contacts;
    private String[] contacts_array;
    ListView contact_result_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_screen);
        contact_result_list = findViewById(R.id.contact_result_list);
        Intent i =getIntent();
        InitListView(i.getStringArrayExtra("Result list"));

    }

    private void InitListView(String[] result) {
        contacts_array = result;
        contacts = new ArrayList<>(Arrays.asList(contacts_array));
        array_adapter = new ArrayAdapter<>(getBaseContext(), R.layout.searchlist_layout, R.id.searchlist_contact, contacts);
        contact_result_list.setAdapter(array_adapter);

        contact_result_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
                SaveContact(contacts.get(i).toString());
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
        else {
            string.append(name + ",");
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("pref_contacts", string.toString());
        editor.apply();
    }


}
