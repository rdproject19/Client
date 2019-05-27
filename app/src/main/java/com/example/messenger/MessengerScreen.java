package com.example.messenger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Space;

public class MessengerScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout chatlist;
    private ImageButton action_button;
    private int tab_pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_screen);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        action_button = findViewById(R.id.actionButton);

        setSupportActionBar(toolbar);
        viewPager.setAdapter(new ViewPagerAdapter(this));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab_pos = tab.getPosition();
                addButtonListener(action_button);

                switch(tab_pos) {
                    case 0: {
                        ButtonAnimation(getBaseContext(), action_button, BitmapFactory.decodeResource(getResources(), R.drawable.icon_chat));
                        break;
                    }
                    case 1: {
                        ButtonAnimation(getBaseContext(), action_button, BitmapFactory.decodeResource(getResources(), R.drawable.icon_edit));
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        TabLayout.Tab tab1 = tabLayout.getTabAt(1);
        tab1.select();
    }

    private void addButtonListener(ImageButton btn) {
        chatlist = findViewById(R.id.chatlist);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(tab_pos) {
                    case 0: {
                        addChatBox(v);
                        break;
                    }
                    case 1: {
                        startActivity(new Intent(MessengerScreen.this, EditDetailsScreen.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    public static void ButtonAnimation(Context c, final ImageButton v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    // Buttons //
    public void addChatBox(View view) {
        Drawable profile_picture = getResources().getDrawable( R.drawable.icon_default_profile , null);
        Drawable background = getResources().getDrawable( R.drawable.chatbox , null);


        Button chatbox = new Button(this);
        chatbox.setBackground(background);
        //chatbox.setBackgroundColor(Color.parseColor("#e6e6e6"));
        chatbox.setText(" Full name");
        chatbox.setAllCaps(false);
        chatbox.setHeight(300);
        chatbox.setTextSize(30);
        chatbox.setGravity(Gravity.CENTER_VERTICAL);

        //button opens new activity - chat activity.
        chatbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MessengerScreen.this, ChatWindow.class);
                startActivity(i);
            }
        });

        profile_picture.setBounds( 0, 0, 200, 200 );
        chatbox.setCompoundDrawables( profile_picture, null, null, null );
        chatbox.setPadding(50, 0, 0, 0);
        chatlist.addView(chatbox);

        Space space = new Space(this);
        space.setMinimumHeight(0);
        chatlist.addView(space);
    }
}
