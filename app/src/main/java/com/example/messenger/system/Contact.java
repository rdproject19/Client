package com.example.messenger.system;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Contact {
    @PrimaryKey @NonNull
    private String username;

    @ColumnInfo(name = "full name")
    private String fullName;

    @ColumnInfo(name = "ImagePath")
    private String imagePath;

    public Contact(@NonNull String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
    }

    public Contact(@NonNull String username, String fullName, String imagePath) {
        this.username = username;
        this.fullName = fullName;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
