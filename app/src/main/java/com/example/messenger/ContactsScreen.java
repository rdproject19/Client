package com.example.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.messenger.system.http.User;

import org.w3c.dom.Text;

import java.util.List;

public class ContactsScreen extends AppCompatActivity {

    private EditText search_bar;
    private String result_list;
    private TextView error_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_screen);
        search_bar = findViewById(R.id.searchContactBar);
        error_text = findViewById(R.id.searchErrorText);
        ((Global) this.getApplication()).stopAlarm();
    }

    public void search(View view) {
        try {
            result_list= User.getName(search_bar.getText().toString());
            if(!result_list.equals("")) {
                Intent j = new Intent(ContactsScreen.this, ContactResult.class);
                j.putExtra("fullname", result_list);
                j.putExtra("username", search_bar.getText().toString());
                startActivity(j);
                finish();
            } else {
                error_text.setText("Username not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ((Global) this.getApplication()).startAlarm();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Global) this.getApplication()).stopAlarm();
    }
}
