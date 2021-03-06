package com.example.trydisslow;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class addAssignment extends AppCompatActivity {

    @SuppressLint({"NewApi", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        getSupportActionBar().hide();
        Random r = new Random();
        CheckBox hour = findViewById(R.id.notificationAssignmentOneHourBefore);
        CheckBox twfohour = findViewById(R.id.notificationAssignmentTwentyFourHoursBefore);
        CheckBox foeihour = findViewById(R.id.notificationAssignmentFourtyEightHoursBefore);
        TimePicker timeDue = findViewById(R.id.timeDue);
        DatePicker dateDue = findViewById(R.id.dateDue);

        //creates a list of modules saved, then saves their codes for selection, and sets adapter to it
        Spinner moduleCodeList = findViewById(R.id.moduleCodeListGrades);
        ArrayList < String > moduleCodeArray = new ArrayList<>();
        ArrayList < Module > moduleList = getAllModules();
        for (Module m:
                moduleList) {
            moduleCodeArray.add(m.moduleCode);

        }
        ArrayAdapter < String > moduleCodeAdapter = new ArrayAdapter < > (this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);
        moduleCodeList.setAdapter(moduleCodeAdapter);

        //assignment enter box
        EditText assignmentTitle = findViewById(R.id.assignmentTitleInput);
        assignmentTitle.setOnTouchListener((v, event) -> {
            assignmentTitle.setText(""); // remove the text when clicked on
            return false;
        });

        //notes enter box
        EditText assignmentNotes = findViewById(R.id.addAssignmentNotesBox);
        assignmentNotes.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                assignmentNotes.setText("");
                return false;
            }
        });

        // save assignment by creating a new assignment object
        AppCompatButton saveAssignmentButton = (AppCompatButton) findViewById(R.id.buttonSaveAssignment);
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
                if (hour.isChecked()) {
                    hourID = r.nextInt(1000);
                    scheduleAssignmentNotification(addAssignment.this, calculateDelay(d, 1), hourID, "1 Hour Warning", title + "due soon");
                    Toast.makeText(addAssignment.this, "scheduled", Toast.LENGTH_LONG).show();
                }
                if (twfohour.isChecked()) {
                    tfHourID = r.nextInt(1000);


                    scheduleAssignmentNotification(addAssignment.this, calculateDelay(d, 24), tfHourID, "24 Hour Warning", title + " due soon");
                    Toast.makeText(addAssignment.this, "scheduled", Toast.LENGTH_LONG).show();
                }
                if (foeihour.isChecked()) {

                    feHourID = r.nextInt(1000);
                    scheduleAssignmentNotification(addAssignment.this, calculateDelay(d, 48), feHourID, "48 Hour Reminder", title + "due soon");
                    Toast.makeText(addAssignment.this, "scheduled", Toast.LENGTH_LONG).show();
                }

                Assignment a = new Assignment(title, d, moduleCodeInQuestion, notes, hourID, tfHourID, feHourID);
                addAssignmentToDb(a);
// add the created object to the database
                startActivity(new Intent(addAssignment.this, Assignments.class));
                Toast.makeText(addAssignment.this, "scheduled", Toast.LENGTH_LONG).show();
            }
        });

    }
    // this method calculates how long to delay the alarm in millis
    @RequiresApi(api = Build.VERSION_CODES.N)
    public long calculateDelay(Date due, int hours) {

        new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        long dueMillis = due.getTime();
        long nowMillis = date.getTime();

        long beforeMillis = hours * 60 * 60 * 1000;

        long delay = dueMillis - nowMillis - beforeMillis;

        return delay;
    }

    public void scheduleAssignmentNotification(Context context, long delay, int notificationId, String title, String content) { //delay is calculated in millis from now

        String CHANNEL_ID = "MYCHANNEL";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID).setContentTitle(title).setContentText(content).setAutoCancel(true).setSmallIcon(R.drawable.alarm).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
       // this builds the actual notification
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);
        Notification notification = builder.build();//completes the notification
        Intent assignmentNotificationIntent = new Intent(context, MyNotificationHelper.class);
        assignmentNotificationIntent.putExtra(MyNotificationHelper.NOTIFICATION_ID, notificationId); // to keep them matched throughout the system (within assignment)
        assignmentNotificationIntent.putExtra(MyNotificationHelper.NOTIFICATION, notification); // to be recieved on the other side
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, assignmentNotificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        long futureInMilliSeconds = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMilliSeconds, pendingIntent); // sets alarm to wake up
    }


// this method calls the database, and returns a full list of every module in it
    public ArrayList < Module > getAllModules() {
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        ArrayList < Module > moduleList = new ArrayList < > ();

        Cursor moduleQuery = myBase.rawQuery("SELECT * FROM Modules", null);
        if (moduleQuery.moveToFirst()) {
            String name = moduleQuery.getString(0);
            String notes = moduleQuery.getString(3);
            String leader = moduleQuery.getString(2);
            String moduleCode = moduleQuery.getString(1);
            Module m = new Module(leader, name, moduleCode, notes);
            moduleList.add(m);
            while (moduleQuery.moveToNext()) {
                name = moduleQuery.getString(0);
                notes = moduleQuery.getString(3);
                leader = moduleQuery.getString(2);
                moduleCode = moduleQuery.getString(1);
                m = new Module(leader, name, moduleCode, notes);
                moduleList.add(m); // adds each to the list
            }
        }
        return moduleList;  // returns list
    }

    // this method takes in an assignment, and inserts it to the database
    public void addAssignmentToDb(Assignment a) {
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        java.text.SimpleDateFormat sdfToString = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDate = sdfToString.format(a.dueDate);
        String insertStatement = "INSERT INTO Assignments2 VALUES('" + a.title + "','" + a.whichModuleIsTaskFor + "','" + stringDate + "','" + a.notes + "'," + a.assignmentId + "," + a.hourID + "," + a.tfHourId + "," + a.feHourId + ");";
        myBase.execSQL(insertStatement);
    }


}