package com.example.messenger;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.amitshekhar.DebugDB;
import com.example.messenger.system.AppDatabase;
import com.example.messenger.system.ChatHandler;
import com.example.messenger.system.Keys;
import com.example.messenger.system.Message;
import com.example.messenger.system.UserData;
import com.example.messenger.system.WebAPI;

/**
 * Class that handles global logic
 * @author Karim Abdulahi
 */
public class Global extends Application {

    private ChatHandler chatHandler;
    private UserData userdata;
    private Context context;
    private AppDatabase db;
    private WebAPI webAPI;

    /**
     * Get current chatHandler object
     *
     * @return chathandler
     */
    public ChatHandler getChatHandler() {
        return this.chatHandler;
    }

    /**
     * Get userData
     * @return userData object {@link UserData}
     */
    public UserData getUserData()
    {
        return this.userdata;
    }

    /**
     * If app starts for first time, the chathandler must be initialised.
     */
    public void initChatHandler() {
        this.chatHandler = new ChatHandler(this);
    }


    /**
     * Access database instance
     * @return AppDatabase
     */
    public AppDatabase db()
    {
        return this.db;
    }

    /**
     * All things to do when application is first started.
     */
    @Override
    public void onCreate()
    {
        super.onCreate();

        this.context = getApplicationContext();
        this.userdata = new UserData(this.context);

        /* @TODO remove allowMainThreadQueries */
        this.db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database").allowMainThreadQueries().build();


    }


}
