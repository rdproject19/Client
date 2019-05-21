package com.example.messenger;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
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

        LinearLayout chatlist = findViewById(R.id.Chats);

        Drawable profile_picture = this.getBaseContext().getResources().getDrawable( R.drawable.icon_default_profile , null);
        Drawable background = this.getBaseContext().getResources().getDrawable( R.drawable.chatbox , null);

        Button chatbox = new Button(this);
        chatbox.setBackground(background);
        chatbox.setText(" Full name");
        chatbox.setAllCaps(false);
        chatbox.setHeight(300);
        chatbox.setTextSize(30);
        chatbox.setGravity(Gravity.CENTER_VERTICAL);
        profile_picture.setBounds( 0, 0, 200, 200 );
        chatbox.setCompoundDrawables( profile_picture, null, null, null );
        chatbox.setPadding(50, 0, 0, 0);
        chatlist.addView(chatbox);
    }
}