package com.example.messenger;

import android.app.Application;
import android.content.Context;

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
     * All things to do when application is first started.
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        this.context = getApplicationContext();
        this.chatHandler = new ChatHandler(context);
        this.userdata = new UserData(this.context);
    }


}
