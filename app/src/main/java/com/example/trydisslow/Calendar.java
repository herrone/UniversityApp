package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
       // TextView cal = (TextView)findViewById(R.id.classBox1);
        Button modulesAndClassesButton = (Button)findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, ModulesAndClasses.class));
            }
        });
        Button assignmentsButton = (Button) findViewById(R.id.buttonAssignments);
        assignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, Assignments.class));
            }
        });


        Button settingsButton = (Button)findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleNotification(Calendar.this, 0, 2, "sent", "this worked");
                startActivity(new Intent(Calendar.this, Settings.class));
            }
        });
        Button calendarButton = (Button) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Calendar.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });


    }
    public void scheduleNotification(Context context, long delay, int notificationId, String title, String content) {//delay is after how much time(in millis) from current time you want to schedule the notification
        String CHANNEL_ID="MYCHANNEL";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.alarm)
                //.setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.drawable.app_icon)).getBitmap())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}