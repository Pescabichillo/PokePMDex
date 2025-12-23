package com.example.pokepmdex;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationHandler extends ContextWrapper {
    private NotificationManager manager;
    public static final String CHANNEL_HIGH_ID = "1";
    public static final String CHANNEL_LOW_ID = "2";
    private final String CHANNEL_HIGH_NAME = "HIGH CHANNEL";
    private final String CHANNEL_LOW_NAME = "LOW CHANNEL";
    private final String GROUP_NAME = "GROUP";
    public static final int GROUP_ID= 111;

    public NotificationHandler(Context base) {
        super(base);
        createChannels();
    }

    public NotificationManager getManager() {
        if (manager==null)
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        return manager;
    }

    public void createChannels() {
        NotificationChannel highChannel = new NotificationChannel(CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME, NotificationManager.IMPORTANCE_HIGH);
        // Configuration
        highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getManager().createNotificationChannel(highChannel);
    }

    public Notification.Builder createNotification (String title, String msg) {
        if (Build.VERSION.SDK_INT>= 26) {
            return createNotificationChannels(title, msg, CHANNEL_HIGH_ID);
        }
        return createNotificationNoChannels(title, msg);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder createNotificationChannels (String title, String msg, String channel) {

        Intent intent = new Intent(this, Pokemon_favourites.class);
        intent.putExtra("title", title);
        intent.putExtra("msg", msg);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        return  new Notification.Builder(getApplicationContext(), channel)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setGroup(GROUP_NAME)
                .setContentIntent(pendingIntent);
    }

    private Notification.Builder createNotificationNoChannels (String title, String msg) {

        Intent intent = new Intent(this, Pokemon_favourites.class);
        intent.putExtra("title", title);
        intent.putExtra("msg", msg);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        return new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setGroup(GROUP_NAME)
                .setContentIntent(pendingIntent);
    }
    public void publishGroup () {

        String channel= CHANNEL_HIGH_ID;

        Notification groupNotification = new Notification.Builder(getApplicationContext(), channel)
                .setGroup(GROUP_NAME)
                .setGroupSummary(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground).build();
        getManager().notify(GROUP_ID,groupNotification);

    }
}
