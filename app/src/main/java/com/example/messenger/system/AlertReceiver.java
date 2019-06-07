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
        try {
            if(!is_running) {
                addNotification(context);
            } else {
                addNotification(context);
            }

            if( msg!=null) {
                Message last_message = new Message(msg);
                if(!is_running) {

                } else {
                    sendNotification(context, last_message);
                }
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

    public void sendNotification(Context context, Message last_message){
        notification = new NotificationCompat.Builder(context, "2");
        notification.setAutoCancel(true);

        //build the notification
        notification.setSmallIcon(R.drawable.icon_chat);
        notification.setTicker("You have a new message!");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(last_message.getSenderID());
        notification.setContentText(last_message.getMessage());

        //select what happens, when user clicks the notification
        Intent intent = new Intent(context, ChatWindow.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //"send" the notification
        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nm.notify(4, notification.build());
    }

    private void addNotification(Context context) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon_round)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(context, ChatWindow.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
