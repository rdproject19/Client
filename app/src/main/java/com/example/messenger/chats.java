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
import android.widget.Space;

public class chats extends AppCompatActivity {

    LinearLayout chatlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats);

        chatlist = findViewById(R.id.Chats);
    }

    public void openProfile(View v) {
        Intent i = new Intent(chats.this, profile.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

    public void newChat(View view) {
        Drawable profile_picture = this.getBaseContext().getResources().getDrawable( R.drawable.icon_default_profile , null);
        Drawable background = this.getBaseContext().getResources().getDrawable( R.drawable.chatbox , null);

        Button chatbox = new Button(this);
        chatbox.setBackground(background);
        chatbox.setText(" Full name");
        chatbox.setAllCaps(false);
        chatbox.setHeight(300);
        chatbox.setTextSize(30);
        chatbox.setGravity(Gravity.CENTER_VERTICAL);

        //button opens new activity - chat activity.
        chatbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(chats.this, chatWindow.class);
                startActivity(i);
            }
        });

        profile_picture.setBounds( 0, 0, 200, 200 );
        chatbox.setCompoundDrawables( profile_picture, null, null, null );
        chatbox.setPadding(50, 0, 0, 0);
        chatlist.addView(chatbox);

        Space space = new Space(this);
        space.setMinimumHeight(30);
        chatlist.addView(space);
    }
}
