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

    NotificationCompat.Builder notification;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("It works");
        boolean is_running = isAppRunning(context, "com.example.messenger");
        Global global = ((Global) context.getApplicationContext());
        UserData ud = global.getUserData();
        global.getChatHandler().sendUpdateRequest();
        String msg = ud.getString(Keys.LASTMESSAGE);
        addNotification2(context, "test");
        try {
            if( msg!=null) {
                Message last_message = new Message(msg);
                addNotification(context, last_message);
                ud.setString(Keys.LASTMESSAGE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAppRunning(final Context context, final String packageName) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> l = activityManager.getRunningAppProcesses();

        if(l.size() != 0) {
            for(ActivityManager.RunningAppProcessInfo r : l) {
                if(r.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addNotification(Context context, Message msg) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon_round)
                        .setContentTitle(msg.getSenderID())
                        .setContentText(msg.getMessage());

        Intent notificationIntent = new Intent(context, ChatWindow.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void addNotification2(Context context, String test) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon_round)
                        .setContentTitle(test)
                        .setContentText(test);

        Intent notificationIntent = new Intent(context, ChatWindow.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
