package com.example.trydisslow;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class editAssignment extends AppCompatActivity {
    String name;
    String notes;
    String code;
    String dueD;
    int hourId = 0;
    int tfHourId = 0;
    int feHourId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_assignment);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String editableAssignment = intent.getExtras().getString("editableAssignment");

        Toast.makeText(this,editableAssignment,
                Toast.LENGTH_SHORT).show();
        Spinner moduleCodeList = findViewById(R.id.moduleCodeList);
        ArrayList<String> moduleCodeArray = new ArrayList<String>();


        //  moduleCodeArray[0] = "choose a module";





//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.


        EditText assignmentTitle = (EditText) findViewById(R.id.assignmentTitleInput);
        EditText assignmentNotes = (EditText) findViewById(R.id.addAssignmentNotesBox);
        TimePicker timeDue = (TimePicker) findViewById(R.id.timeDue);
        DatePicker dateDue = (DatePicker) findViewById(R.id.dateDue);
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        String retrieveDetails = "SELECT * FROM NEWASSIGNMENTSWITHIDS2 WHERE title = '" + editableAssignment + "'";
        Cursor assignmentDetails = myBase.rawQuery(retrieveDetails, null);

        if (assignmentDetails.moveToFirst()) {
            name = assignmentDetails.getString(0);
            code = assignmentDetails.getString(1);
            dueD = assignmentDetails.getString(2);
            notes = assignmentDetails.getString(3);
            hourId = assignmentDetails.getInt(5);
            tfHourId = assignmentDetails.getInt(6);
            feHourId = assignmentDetails.getInt(7);

            assignmentNotes.setText(notes);
            assignmentTitle.setText(name);
            moduleCodeArray.add(code);
            ArrayAdapter<String> moduleCodeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);

            // ArrayAdapter<String> moduleCodeArray = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodes);
//set the spinners adapter to the previously created one.
            moduleCodeList.setAdapter(moduleCodeAdapter);
        }
        else {}
        AppCompatButton saveAssignmentButton = (AppCompatButton) findViewById(R.id.buttonSaveAssignment);
        saveAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //Random random = new Random();
              //  int notificationId = 0;
             //  Cursor moduleQuery = myBase.rawQuery("SELECT * FROM NEWASSIGNMENTSWITHIDS WHERE title = " + editableAssignment + ";", null);
              //  title TEXT, code TEXT, dueDate TEXT, notes TEXT, id INT, hID INT, tfID INT, feID INT
//                if(moduleQuery.moveToFirst()) {
//                   int hourCode = moduleQuery.getInt(5);
//                    int thourCode = moduleQuery.getInt(6);
//                    int fhourCode = moduleQuery.getInt(7);
//                 if (hourCode != 0){
//                     Context context = editAssignment.this;
//                     notificationId = random.nextInt();
//                     Intent intent = new Intent(context, MainActivity.class);
//                     PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//                     scheduleNotification(editAssignment.this, delayCalculator(d, 24), r.nextInt(100), "24 Hour Reminder", title + "due soon");
//                 }
//                    if (thourCode != 0){
//
//                    }
//                    if (fhourCode != 0){
//
//                    }
//
//                }

                String deleteStatement = "DELETE FROM NEWASSIGNMENTWITHIDS WHERE title = '" + editableAssignment + "'";
                if (hourId > 0){
                    Intent myIntent = new Intent(editAssignment.this, Assignments.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(editAssignment.this, hourId, myIntent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
                if (tfHourId > 0){
                    Intent myIntent = new Intent(editAssignment.this, Assignments.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(editAssignment.this, tfHourId, myIntent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
                if (feHourId > 0){
                    Intent myIntent = new Intent(editAssignment.this, Assignments.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(editAssignment.this, feHourId, myIntent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
                myBase.execSQL(deleteStatement);
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
                int hourID = 0;
                int tfHourID = 0;
                int feHourID = 0;
                CheckBox hour = findViewById(R.id.notificationAssignmentOneHourBefore);
                CheckBox twfohour = findViewById(R.id.notificationAssignmentTwentyFourHoursBefore);
                CheckBox foeihour = findViewById(R.id.notificationAssignmentFourtyEightHoursBefore);
                Random r = new Random();
                if(hour.isChecked()){
                    Toast.makeText(editAssignment.this, "1 hour",
                            Toast.LENGTH_LONG).show();
                    hourID = r.nextInt(1000);
                    scheduleNotification(editAssignment.this, delayCalculator(d, 1), r.nextInt(100), "1 Hour Reminder", title + "due soon");

                    //  scheduleNotification(addAssignment.this, delayCalculator(d, 1),r.nextInt() , "1 Hour Warning", title);
                }
                if(twfohour.isChecked()){
                    tfHourID = r.nextInt(1000);

                    scheduleNotification(editAssignment.this, delayCalculator(d, 24), r.nextInt(100), "24 Hour Reminder", title + "due soon");
                    //  scheduleNotification(addAssignment.this, delayCalculator(d, 24), r.nextInt(), "24 Hour Warning", title);
                }
                if(foeihour.isChecked()){
                    //  scheduleNotification(addAssignment.this, delayCalculator(d, 48), r.nextInt(), "48 Hour Warning", title);
                    feHourID = r.nextInt(1000);
                    scheduleNotification(editAssignment.this, delayCalculator(d, 48), r.nextInt(100), "48 Hour Reminder", title + "due soon");
                }


                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
//                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                alarmManager.cancel();
                Assignment a = new Assignment(title, d.toString(), moduleCodeInQuestion, notes, hourID, tfHourID, feHourID);
                myBase.execSQL("CREATE TABLE if not exists NEWASSIGNMENTWITHIDS2(title TEXT, code TEXT, dueDate TEXT, notes TEXT, id TEXT, hID INT, tfID INT, feID INT);");
                String insertStatement = "INSERT INTO NEWASSIGNMENTWITHIDS2 VALUES('" + a.title + "','" + a.whichModuleIsTaskFor + "','" + a.dueDate + "','" + a.notes + "'," + a.assignmentId +"," + a.hourID +"," + a.tfHourId +"," + a.feHourId +");";
                // String insertStatement = "INSERT INTO Modules VALUES('" + m.nameMod + "','"+ m.moduleCode + "','"  + m.courseLeader + "','"  + m.modNotes + "')\"";
                myBase.execSQL(insertStatement);
                Toast.makeText(editAssignment.this, "Time of " + a.dueDate,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(editAssignment.this, Assignments.class));

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


    @RequiresApi(api = Build.VERSION_CODES.N)
    public long delayCalculator(Date due, int hours){

       // SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        long dueMillis = due.getTime();
        long nowMillis = date.getTime();

        long beforeMillis = hours * 60 * 60 * 1000;


        long delay = dueMillis - nowMillis - beforeMillis;
        return delay;
    }
}
//       }
