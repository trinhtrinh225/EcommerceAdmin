package com.example.e_commerceadmin.ThongBao;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.annotation.Target;

public class OreoandAboveNotification extends ContextWrapper {
    private static final String ID=  "some-id";
    private static final String NAME=  "FirebaseAPP";
    private NotificationManager notificationManager;
    public OreoandAboveNotification(Context base)
    {
        super(base);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
    }
    public NotificationManager getManager() {
        if(notificationManager == null) {
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return  notificationManager;
    }
    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getNotifications(String title, String body, PendingIntent pIntent, Uri soundUri, String icon)
    {
        return new Notification.Builder(getApplicationContext(), ID).setContentIntent(pIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(soundUri)
                .setAutoCancel(true)
                .setSmallIcon(Integer.parseInt(icon));
    }
}
