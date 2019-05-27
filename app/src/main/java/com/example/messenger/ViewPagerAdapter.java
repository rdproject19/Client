package com.example.messenger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Space;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private String[] tabTitles = {"Chats", "Profile", "Button"};
    LinearLayout chatlist;

    ViewPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int resource = 0;

        switch (position) {
            case 0: {
                resource = R.layout.tab_chats;
                break;
            }
            case 1: {
                resource = R.layout.tab_profile;
                break;
            }
            case 2: {
                resource = R.layout.tab_profile;
                break;
            }
        }

        View view = inflater.inflate(resource, container, false);

        /*
        switch (position) {
            case 0: {
                ImageButton btn = ((Activity) mContext).findViewById(R.id.actionButton);
                chatlist = view.findViewById(R.id.chatlist);

                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        addChatBox(v);
                    }
                });
                break;
            }
            case 1: {
                //action for profile tab
                break;
            }
            case 2: {
                //action for ? tab
                break;
            }
        }*/

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    // Buttons //
    public void addChatBox(View view) {
        Drawable profile_picture = mContext.getResources().getDrawable( R.drawable.icon_default_profile , null);
        Drawable background = mContext.getResources().getDrawable( R.drawable.chatbox , null);

        Button chatbox = new Button(mContext);
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
                Intent i = new Intent(mContext, ChatWindow.class);
                mContext.startActivity(i);
            }
        });

        profile_picture.setBounds( 0, 0, 200, 200 );
        chatbox.setCompoundDrawables( profile_picture, null, null, null );
        chatbox.setPadding(50, 0, 0, 0);
        chatlist.addView(chatbox);

        Space space = new Space(mContext);
        space.setMinimumHeight(0);
        chatlist.addView(space);
    }
}
