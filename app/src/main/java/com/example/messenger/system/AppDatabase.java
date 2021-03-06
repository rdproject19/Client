package com.example.messenger.system;

        import android.arch.persistence.room.Database;
        import android.arch.persistence.room.RoomDatabase;
        import android.arch.persistence.room.TypeConverters;


@Database(
        entities = {Message.class, Conversation.class, Contact.class},
        version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase
{
    public abstract MessageDao messageDao();
    public abstract ConversationDao conversationDao();
    public abstract ContactDao contactDao();
}