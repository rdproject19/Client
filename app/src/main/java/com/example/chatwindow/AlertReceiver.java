package com.example.chatwindow;

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


    public void sendNotification(){
        //build the notification
        notification.setSmallIcon(R.drawable.icon_chat);
        notification.setTicker("You have a new notification!");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("title");
        notification.setContentText("Content text");


        //select what happens, when user clicks the notification
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //"send" the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(4, notification.build());
    }

}
