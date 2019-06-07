package com.example.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.messenger.system.http.User;

import java.util.List;

public class ContactsScreen extends AppCompatActivity {

    private EditText search_bar;
    private String result_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_screen);
        search_bar = findViewById(R.id.searchContactBar);
    }

    public void search(View view) {
        try {
            result_list= User.getName(search_bar.toString());
            Intent j = new Intent(ContactsScreen.this, ContactResult.class);
            System.out.println(result_list);
            j.putExtra("Result list", result_list);
            startActivity(j);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
