package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.annotation.RequiresApi;
import android.annotation.TargetApi;

public class Settings extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
      //show_Notification();

        Button modulesAndClassesButton = (Button)findViewById(R.id.buttonModulesAndClasses);

        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, ModulesAndClasses.class));
            }
        });

        Button settingsButton = (Button)findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Settings.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });
        Button calendarButton = (Button)findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Calendar.class));
            }
        });
        Button assignmentButton = (Button)findViewById(R.id.buttonAssignments);
        assignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, Assignments.class));

            }
        });



    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(){

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText("Clicked on")
                .setContentTitle("shown")
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);

    }
    public void Settings(){

    }
}