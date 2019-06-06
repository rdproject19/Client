package com.example.messenger.system;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact WHERE username = :usrname LIMIT 1")
    Contact getFullName(String usrname);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void putConversation(Conversation c);
}
