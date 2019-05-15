package com.example.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class loginScreen extends AppCompatActivity {

    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        username.requestFocus();
    }

    public void openChats(View v) {
        //get the username and password, check if they are correct - login (open main activity)
        //prevent login

        startActivity(new Intent(loginScreen.this, chats.class));

    }
}
