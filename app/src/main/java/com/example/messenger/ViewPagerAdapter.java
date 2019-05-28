package com.example.messenger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import static android.content.Context.MODE_PRIVATE;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private String[] tabTitles = {"Chats", "Profile", "Contacts"};
    private static final String prefs_name = "PrefsFile";
    private ImageView profile_picture;

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
        SharedPreferences prefs = mContext.getSharedPreferences(prefs_name, MODE_PRIVATE);
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

        switch (position) {
            case 0: {
                //actions for chats tab
                break;
            }
            case 1: {
                //actions for profile tab
                profile_picture = view.findViewById(R.id.profilePicture);

                SharedPreferences sp = mContext.getSharedPreferences(prefs_name, MODE_PRIVATE);

                if(sp.contains("pref_bm")) {
                    String profile_pic = sp.getString("pref_bm", "not found.");
                    byte[] imageAsBytes = Base64.decode(profile_pic.getBytes(), Base64.DEFAULT);
                    profile_picture.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                }

                break;
            }
            case 2: {
                //actions for contacts tab
                break;
            }
        }

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
}
