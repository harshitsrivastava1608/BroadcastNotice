package com.example.project4.Notify;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.project4.R;

public class NotifHelper {
    public static void displayNotification(Context context,String title,String body){
        NotificationCompat.Builder mbuilder=new NotificationCompat.Builder(context,
                MainNotify.ChID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,mbuilder.build());
    }
}
