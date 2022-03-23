package com.example.trydisslow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.icu.util.Calendar;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class addAssignment extends AppCompatActivity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        Random r = new Random(1000);

        //  Assignment a = new Assignment();
        //        Intent intent = getIntent();
        //       if (intent.hasExtra("editableAssignment")){
        //
        //            Toast. makeText(getApplicationContext(), "Got it", Toast. LENGTH_LONG);
        //        } else {

        Spinner moduleCodeList = findViewById(R.id.moduleCodeList);
        ArrayList < String > moduleCodeArray = new ArrayList < String > ();
        CheckBox hour = findViewById(R.id.notificationAssignmentOneHourBefore);
        CheckBox twfohour = findViewById(R.id.notificationAssignmentTwentyFourHoursBefore);
        CheckBox foeihour = findViewById(R.id.notificationAssignmentFourtyEightHoursBefore);

        //  moduleCodeArray[0] = "choose a module";

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        Cursor query = myBase.rawQuery("SELECT * FROM NEWMODULE3", null);

        if (query.moveToFirst()) {
            String name = query.getString(0);
            moduleCodeArray.add(name);
            if (query.moveToNext()) {
                String name2 = query.getString(0);
                moduleCodeArray.add(name2);
            }
            if (query.moveToNext()) {
                String name3 = query.getString(0);
                moduleCodeArray.add(name3);
            }

        }
        //moduleCodeArray.add("a");
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.

        ArrayAdapter < String > moduleCodeAdapter = new ArrayAdapter < > (this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);

        // ArrayAdapter<String> moduleCodeArray = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodes);
        //set the spinners adapter to the previously created one.
        moduleCodeList.setAdapter(moduleCodeAdapter);
        EditText assignmentTitle = (EditText) findViewById(R.id.assignmentTitleInput);
        EditText assignmentNotes = (EditText) findViewById(R.id.addAssignmentNotesBox);
        TimePicker timeDue = (TimePicker) findViewById(R.id.timeDue);
        DatePicker dateDue = (DatePicker) findViewById(R.id.dateDue);
        Settings settings = new Settings();
        Button saveAssignmentButton = (Button) findViewById(R.id.buttonSaveAssignment);
        saveAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int hourID = 0;
                int tfHourID = 0;
                int feHourID = 0;
                String notes = assignmentNotes.getText().toString();
                String title = assignmentTitle.getText().toString();
                String moduleCodeInQuestion = moduleCodeList.getSelectedItem().toString();
                Date d = new Date();
                d.setHours(timeDue.getHour());
                d.setMinutes(timeDue.getMinute());
                d.setSeconds(0);
                d.setYear(dateDue.getYear());
                d.setMonth(dateDue.getMonth());
                d.setDate(dateDue.getDayOfMonth());
                show_Notification();

                //scheduleNotification(com.example.trydisslow.addAssignment.this, 0, 2, "sent", "this worked");
                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
                if (hour.isChecked()) {
                    Toast.makeText(addAssignment.this, "1 hour",
                            Toast.LENGTH_LONG).show();
                    hourID = r.nextInt(1000);

                    scheduleNotification(addAssignment.this, delayCalculator(d, 1), r.nextInt(100), "1 Hour Reminder", title + "due soon");

                    //  scheduleNotification(addAssignment.this, delayCalculator(d, 1),r.nextInt() , "1 Hour Warning", title);
                }
                if (twfohour.isChecked()) {
                    tfHourID = r.nextInt(1000);

                    scheduleNotification(addAssignment.this, delayCalculator(d, 24), r.nextInt(100), "24 Hour Reminder", title + "due soon");
                    //  scheduleNotification(addAssignment.this, delayCalculator(d, 24), r.nextInt(), "24 Hour Warning", title);
                }
                if (foeihour.isChecked()) {
                    //  scheduleNotification(addAssignment.this, delayCalculator(d, 48), r.nextInt(), "48 Hour Warning", title);
                    feHourID = r.nextInt(1000);
                    scheduleNotification(addAssignment.this, delayCalculator(d, 48), r.nextInt(100), "48 Hour Reminder", title + "due soon");
                }
                Assignment a = new Assignment(title, d.toString(), moduleCodeInQuestion, notes, hourID, tfHourID, feHourID);
                myBase.execSQL("CREATE TABLE if not exists NEWASSIGNMENTWITHIDS2(title TEXT, code TEXT, dueDate TEXT, notes TEXT, id TEXT, hID INT, tfID INT, feID INT);");
                String insertStatement = "INSERT INTO NEWASSIGNMENTWITHIDS2 VALUES('" + a.title + "','" + a.whichModuleIsTaskFor + "','" + a.dueDate + "','" + a.notes + "'," + a.assignmentId + "," + a.hourID + "," + a.tfHourId + "," + a.feHourId + ");";
                // String insertStatement = "INSERT INTO Modules VALUES('" + m.nameMod + "','"+ m.moduleCode + "','"  + m.courseLeader + "','"  + m.modNotes + "')\"";
                myBase.execSQL(insertStatement);
                //                    Toast.makeText(addAssignment.this, "Time of " + a.dueDate,
                //                            Toast.LENGTH_LONG).show();
                startActivity(new Intent(addAssignment.this, Assignments.class));

            }
        });
        Button modulesAndClassesButton = (Button) findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addAssignment.this, ModulesAndClasses.class));
            }
        });

        Button settingsButton = (Button) findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addAssignment.this, Settings.class));
            }
        });
        Button calendarButton = (Button) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addAssignment.this, Calendar.class));
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        String CHANNEL_ID = "MYCHANNEL";
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentText("Clicked on")
                .setContentTitle("shown")
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat, "Title", pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1, notification);

    }
    public void scheduleNotification(Context context, long delay, int notificationId, String title, String content) { //delay is after how much time(in millis) from current time you want to schedule the notification
        String CHANNEL_ID = "MYCHANNEL";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public long delayCalculator(Date due, int hours) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        long dueMillis = due.getTime();
        long nowMillis = date.getTime();

        long beforeMillis = hours * 60 * 60 * 1000;

        long delay = dueMillis - nowMillis - beforeMillis;
        return delay;
    }

}
//       }