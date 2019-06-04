package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.example.messenger.system.ChatHandler;
import com.example.messenger.system.Conversation;

import com.example.messenger.system.Keys;
import com.example.messenger.system.Message;
import com.example.messenger.system.UserData;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class MessengerScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ListView chatlist;
    private ListView contactlist;
    private ImageButton action_button;
    private int tab_pos = 0;

    private SimpleAdapter chat_adapter;
    private List<HashMap<String, String>> chat_array;
    private HashMap<String, Conversation> chat_ids;
    public static SimpleAdapter contact_adapter;
    private List<HashMap<String, String>> contact_array;

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("XD", ex.toString());
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println(getLocalIpAddress());

        setContentView(R.layout.tab_screen);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        action_button = findViewById(R.id.actionButton);

        setSupportActionBar(toolbar);
        viewPager.setAdapter(new ViewPagerAdapter(this));

        InitChatList();
        InitContactList();

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab_pos = tab.getPosition();
                InitTabs();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //just ignore this part, it wont work otherwise
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                chatlist = findViewById(R.id.chatlist);
                chatlist.setAdapter(chat_adapter);
                chatlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
                        Intent j = new Intent(MessengerScreen.this, ChatWindow.class);
                        j.putExtra("conversation", chat_array.get(i).get("convId"));
                        startActivity(j);
                    }
                });

                addButtonListener(action_button);
            }
        }, 10);
    }

    private void InitTabs() {
        addButtonListener(action_button);

        ButtonAnimation(getBaseContext(), action_button, BitmapFactory.decodeResource(getResources(),
                tab_pos == 2 ? R.drawable.icon_contact : tab_pos == 1 ? R.drawable.icon_edit : R.drawable.icon_chat));

        switch(tab_pos) {
            case 0: {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        chatlist = findViewById(R.id.chatlist);
                        chatlist.setAdapter(chat_adapter);
                        chatlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
                                Intent j = new Intent(MessengerScreen.this, ChatWindow.class);
                                startActivity(j);
                            }
                        });
                         ShowChats();
                    }
                }, 20);
                break;
            }
            case 1: {
                ImageView profile_picture = findViewById(R.id.profilePicture);
                TextView user_full_name = findViewById(R.id.userfullname);
                SharedPreferences sp = getSharedPreferences("PrefsFile", MODE_PRIVATE);

                if(sp.contains("pref_bm")) {
                    String profile_pic = sp.getString("pref_bm", "not found.");
                    byte[] imageAsBytes = Base64.decode(profile_pic.getBytes(), Base64.DEFAULT);
                    profile_picture.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                }

                if(sp.contains("pref_fn")) {
                    String full_name = sp.getString("pref_fn", "not found.");
                    user_full_name.setText("Full name: " + full_name);
                }
                break;
            }
            case 2: {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        contactlist = findViewById(R.id.contactlist);
                        contactlist.setAdapter(contact_adapter);
                        ShowContacts();
                    }
                }, 20);
                break;
            }
        }
    }

    private void addButtonListener(ImageButton btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(tab_pos) {
                    case 0: {
                        startActivity(new Intent(MessengerScreen.this, AddChatScreen.class));

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                TabLayout.Tab tab = tabLayout.getTabAt(2);
                                tab.select();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        TabLayout.Tab tab = tabLayout.getTabAt(0);
                                        tab.select();
                                    }
                                }, 200);
                            }
                        }, 100);

                        break;
                    }
                    case 1: {
                        startActivity(new Intent(MessengerScreen.this, EditDetailsScreen.class));
                        break;
                    }
                    case 2: {
                        startActivity(new Intent(MessengerScreen.this, ContactsScreen.class));
                    }
                }
            }
        });
    }

    private void InitChatList() {
        chat_array = new ArrayList<HashMap<String, String>>();
        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};
        chat_adapter = new SimpleAdapter(getBaseContext(), chat_array, R.layout.chatlist_layout, from, to);
    }

    private void InitContactList() {
        contact_array = new ArrayList<HashMap<String, String>>();
        String[] from2 = {"contactview_image", "contactview_title"};
        int[] to2 = {R.id.contactview_image, R.id.contactview_item_title};
        contact_adapter = new SimpleAdapter(getBaseContext(), contact_array, R.layout.contactlist_layout, from2, to2);
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
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    private void ShowContacts() {
        contact_array.clear();
        SharedPreferences sp = getSharedPreferences("PrefsFile", MODE_PRIVATE);

        if(sp.contains("pref_contacts")) {
            String list = sp.getString("pref_contacts", "");
            String[] savedcontacts = list.split(",");

            if(savedcontacts.length > 0) {
                for(String c:savedcontacts) {
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("contactview_title", c);
                    hm.put("contactview_image", Integer.toString(R.drawable.icon_default_profile));
                    contact_array.add(hm);
                }
            }
        }
    }

    private void ShowChats() {

        chat_array.clear();

        ChatHandler ch = ((Global) this.getApplication()).getChatHandler();
        UserData ud = ((Global) this.getApplication()).getUserData();

        HashMap<String, Conversation> ConvoMap = ch.ch().getConversations(50);

        for(Conversation convo : ConvoMap.values()) {
            HashMap<String, String> hm = new HashMap<>();
            // username of recipient
            hm.put("listview_title", convo.recipient(ud.getString(Keys.USERNAME)));

            // last message in conversation
            TreeMap<Integer, Message> sorted = convo.getSortedMessages();
            Message last = sorted.lastEntry().getValue();

            hm.put("listview_discription", last.getSenderID() + ": " + last.getMessage());

            // profile image of recipient/sender
            hm.put("listview_image", Integer.toString(R.drawable.icon_default_profile));

            // The conversation ID
            hm.put("convId", convo.getConversationId());

            chat_array.add(hm);

        }

        /*
        chat_array.clear();
        SharedPreferences sp = getSharedPreferences("PrefsFile", MODE_PRIVATE);

        if(sp.contains("pref_chats")) {
            String list = sp.getString("pref_chats", "");
            String[] savedchats = list.split(",");

            if(savedchats.length > 0) {
                for(String c:savedchats) {
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("listview_title", c);
                    hm.put("listview_discription", "boe");
                    hm.put("listview_image", Integer.toString(R.drawable.icon_default_profile));
                    chat_array.add(hm);
                }
            }
        }
        */
    }

    @Override
    public void onResume()
    {
        super.onResume();
        InitTabs();
    }
}
