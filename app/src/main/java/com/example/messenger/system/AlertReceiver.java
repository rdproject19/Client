package com.example.messenger.system;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.example.messenger.ChatWindow;
import com.example.messenger.Global;
import com.example.messenger.R;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;


public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Global global = ((Global) context.getApplicationContext());
            UserData ud = global.getUserData();
            global.getChatHandler().sendUpdateRequest();
            String msg = ud.getString(Keys.LASTMESSAGE);
            if( msg!=null) {
                Message last_message = new Message(msg);
                addNotification(context, last_message);
                ud.setString(Keys.LASTMESSAGE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addNotification(Context context, Message msg) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_round);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setLargeIcon(bitmap)
                        .setContentTitle(msg.getSenderID())
                        .setContentText(msg.getMessage())
                        .setVibrate(new long[]{0, 250, 100, 250});

        Intent notificationIntent = new Intent(context, ChatWindow.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
