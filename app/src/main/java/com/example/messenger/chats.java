package com.example.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class chats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats);

    }

    public void openProfile(View v) {

        Intent i = new Intent(chats.this, profile.class);
        finish();
        startActivity(i);

    }

    public void newChat(View view) {
        LinearLayout ll = findViewById(R.id.Chats);
        Button btn = new Button(this);
        btn.setText("test");
        ll.addView(btn);
    }
}
