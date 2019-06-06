package com.example.messenger;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.messenger.system.AppDatabase;
import com.example.messenger.system.ChatHandler;
import com.example.messenger.system.Conversation;
import com.example.messenger.system.GFG;
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
    private RecyclerViewAdapter adapter;

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

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Conversation conversation) {
        adapter = new RecyclerViewAdapter(this, conversation);
    }

    /**
     *
     */
    public void initialize() {
        //userdata.setUsername("koen");
        //Conversation convo = db.conversationDao().getAll().stream().filter((c)->c.getConversationId().equals("5cf0f1c78bd43f6613fbe21e")).findAny().get();
        //Message.makeMessage("banaan", "5cf0f1c78bd43f6613fbe21e", this);
        //Conversation convo = Conversation.newConversation("5cf0f1c78bd43f6613fbe21e", this);
        //convo.update(this);
        //convo.addParticipant("koen1");
        //convo.addParticipant("koen");
        this.chatHandler = new ChatHandler(this);
    }

    public void setData(String username, String password) {
        userdata.setUsername(username);
        userdata.setSeed(GFG.encryptThisString(username+password));
    }

    /**
     * All things to do when application is first started.
     */
    @Override
    public void onCreate()
    {
        this.db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database").allowMainThreadQueries().build();
        this.context = getApplicationContext();
        this.userdata = new UserData(this.context);
        super.onCreate();
        /* @TODO remove allowMainThreadQueries */


    }
}
