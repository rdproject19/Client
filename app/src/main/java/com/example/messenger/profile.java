package com.example.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
    }

    public void openChats(View v) {
        Intent i = new Intent(profile.this, chats.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

}
