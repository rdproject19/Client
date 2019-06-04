package com.example.messenger.system;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MessageDao
{
    @Query("SELECT * FROM Message")
    List<Message> getAll();

    @Query("SELECT * FROM Message WHERE ConvId = :convId")
    List<Message> getFromConversation(int convId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void putMessage(Message msg);

}
