package com.example.messenger;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.amitshekhar.DebugDB;
import com.example.messenger.system.AppDatabase;
import com.example.messenger.system.ChatHandler;
import com.example.messenger.system.UserData;

/**
 * Class that handles global logic
 * @author Karim Abdulahi
 */
public class Global extends Application {

    private ChatHandler chatHandler;
    private UserData userdata;
    private Context context;
    private AppDatabase db;

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
        System.out.println(DebugDB.getAddressLog());
        this.context = getApplicationContext();
        this.chatHandler = new ChatHandler(context);
        this.userdata = new UserData(this.context);

        this.db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database").build();
    }


}
