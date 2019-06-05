package com.example.messenger.system;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ConversationDao
{
    @Query("SELECT * FROM Conversation")
    List<Conversation> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void putConversation(Conversation c);

    @Update
    void updateConversation(Conversation c);
}
