package com.example.trydisslow;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyNotificationHelper extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";

    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(final Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);

        int idNotification = intent.getIntExtra(NOTIFICATION_ID, 0);

        notificationManager.notify(idNotification, notification);
    }
}