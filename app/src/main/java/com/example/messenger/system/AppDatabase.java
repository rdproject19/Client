package com.example.messenger.system;

        import android.arch.persistence.room.Database;
        import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Message.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract MessageDao messageDao();
}