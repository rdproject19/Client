package com.example.messenger.system;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {

    NotificationCompat.Builder notification;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("It works");
    }

}
