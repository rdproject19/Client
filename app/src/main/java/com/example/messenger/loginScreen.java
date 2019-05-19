package com.example.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class loginScreen extends AppCompatActivity {

    EditText username, password;
    TextView error_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        error_text = findViewById(R.id.invalid);

        username.requestFocus();
    }

    public void openChats(View v) {
        //get the username and password, check if they are correct - login (open main activity)
        //prevent login

        String placeholder_un = "Hoi".toLowerCase();
        String placeholder_pw = "hoi".toLowerCase();

        String un = ((TextView) username).getText().toString().toLowerCase();
        String pw = ((TextView) password).getText().toString().toLowerCase();

        if(un.equals(placeholder_un) && pw.equals(placeholder_pw)) {
            startActivity(new Intent(loginScreen.this, chats.class));
        }
        else {
            String invalid_login_details = "Please enter a valid username/password";
            error_text.setText(invalid_login_details);
        }
    }

    public void openRegisterScreen(View v) {
        startActivity(new Intent(loginScreen.this, registerScreen.class));
    }
}
