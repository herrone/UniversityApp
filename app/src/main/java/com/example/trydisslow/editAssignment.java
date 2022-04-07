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

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class editAssignment extends AppCompatActivity {
    int hourId = 0;
    int tfHourId = 0;
    int feHourId = 0;
    Assignment inQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_assignment);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String editableAssignmentCode = intent.getExtras().getString("editableAssignment"); // recieves the intent

        Toast.makeText(this, editableAssignmentCode,
                Toast.LENGTH_SHORT).show();
        Spinner moduleCodeList = findViewById(R.id.moduleCodeListGrades);
        ArrayList < String > moduleCodeArray = new ArrayList < String > ();


        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.


        EditText assignmentTitle = (EditText) findViewById(R.id.assignmentTitleInput);
        EditText assignmentNotes = (EditText) findViewById(R.id.addAssignmentNotesBox);
        TimePicker timeDue = (TimePicker) findViewById(R.id.timeDue);
        DatePicker dateDue = (DatePicker) findViewById(R.id.dateDue); // instantiates the layout elements
        ArrayList < Assignment > assignments = getAllAssignments(); //get all assignments in db
        for (Assignment a:
                assignments) {
            if (a.title.equals(editableAssignmentCode)) { // if any of them match the intent
                inQuestion = a;
                assignmentNotes.setText(a.notes); //set details to it's properties
                assignmentTitle.setText(a.title);
                moduleCodeArray.add(a.whichModuleIsTaskFor);

            }

        }

        ArrayAdapter < String > moduleCodeAdapter = new ArrayAdapter < > (this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);

        //set the spinners adapter to the previously created one.
        moduleCodeList.setAdapter(moduleCodeAdapter);

        AppCompatButton saveAssignmentButton = (AppCompatButton) findViewById(R.id.buttonSaveAssignment);
        saveAssignmentButton.setOnClickListener(new View.OnClickListener() { // if save button is pushed
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) { // check and reset the notifications
                CheckBox hour = findViewById(R.id.notificationAssignmentOneHourBefore);
                CheckBox twfohour = findViewById(R.id.notificationAssignmentTwentyFourHoursBefore);
                CheckBox foeihour = findViewById(R.id.notificationAssignmentFourtyEightHoursBefore);

            if (inQuestion.hourID > 0) {
                    Intent myIntent = new Intent(editAssignment.this, Assignments.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(editAssignment.this, hourId, myIntent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
                if (inQuestion.tfHourId > 0) {
                    Intent myIntent = new Intent(editAssignment.this, Assignments.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(editAssignment.this, tfHourId, myIntent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
                if (inQuestion.feHourId > 0) {
                    Intent myIntent = new Intent(editAssignment.this, Assignments.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(editAssignment.this, feHourId, myIntent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }

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
                Random r = new Random();
                if (hour.isChecked()) {
                    Toast.makeText(editAssignment.this, "1 hour",
                            Toast.LENGTH_LONG).show();
                    hourID = r.nextInt(1000);
                    scheduleNotification(editAssignment.this, delayCalculator(d, 1), r.nextInt(100), "1 Hour Reminder", title + "due soon");
                    }
                if (twfohour.isChecked()) {
                    tfHourID = r.nextInt(1000);

                    scheduleNotification(editAssignment.this, delayCalculator(d, 24), r.nextInt(100), "24 Hour Reminder", title + "due soon");
                }
                if (foeihour.isChecked()) {
                   feHourID = r.nextInt(1000);
                    scheduleNotification(editAssignment.this, delayCalculator(d, 48), r.nextInt(100), "48 Hour Reminder", title + "due soon");
                }
                Assignment a = new Assignment(title, d, moduleCodeInQuestion, notes, hourID, tfHourID, feHourID);
                updateAssignment(a);
                deleteAssignment(inQuestion);

                startActivity(new Intent(editAssignment.this, Assignments.class));

            }
        });


    }
    public void scheduleNotification(Context context, long delay, int notificationId, String title, String content) { //delay is after how much time(in millis) from current time you want to schedule the notification
        String CHANNEL_ID = "MYCHANNEL";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.alarm)
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
    public long delayCalculator(Date due, int hours) { // calculate the delay between now and when we want the notification

        // SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        long dueMillis = due.getTime();
        long nowMillis = date.getTime();

        long beforeMillis = hours * 60 * 60 * 1000;


        long delay = dueMillis - nowMillis - beforeMillis;
        return delay;
    }
    public ArrayList < Assignment > getAllAssignments() { // get all the assignments from the db

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists Assignments(title TEXT, module TEXT, date TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

        ArrayList < Assignment > assignmentList = new ArrayList < > ();
        Cursor query = myBase.rawQuery("SELECT * FROM Assignments2", null);
        if (query.moveToFirst()) {
            String name = query.getString(0);
            String code = query.getString(1);
            String stringDueDate = query.getString(2);
            java.text.SimpleDateFormat sdfBackToDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dueDate = null;
            try {
                dueDate = sdfBackToDate.parse(stringDueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String notes = query.getString(3);
            int id = query.getInt(4);
            int hId = query.getInt(5);
            int tfId = query.getInt(6);
            int feId = query.getInt(7);
            Assignment a = new Assignment(name, dueDate, code, notes, id, hId, tfId, feId);
            assignmentList.add(a);

            while (query.moveToNext()) {
                name = query.getString(0);
                code = query.getString(1);
                stringDueDate = query.getString(2);

                try {
                    dueDate = sdfBackToDate.parse(stringDueDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                notes = query.getString(3);
                id = query.getInt(4);
                hId = query.getInt(5);
                tfId = query.getInt(6);
                feId = query.getInt(7);
                a = new Assignment(name, dueDate, code, notes, id, hId, tfId, feId);
                assignmentList.add(a);
            }
        }

        return assignmentList;
    }
    public ArrayList < Module > getAllModules() { // returns a full list of modules in the db
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists Assignments2(title TEXT, module TEXT, date TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

        ArrayList < Module > moduleList = new ArrayList < > ();
        Module m = new Module();
        Cursor moduleQuery = myBase.rawQuery("SELECT * FROM Modules", null);
        if (moduleQuery.moveToFirst()) {
            m.setNameMod(moduleQuery.getString(0));
            m.setModNotes(moduleQuery.getString(3));
            m.setCourseLeader(moduleQuery.getString(2));
            m.setModuleCode(moduleQuery.getString(1));
            moduleList.add(m);
            while (moduleQuery.moveToNext()) {
                Module mo = new Module();
                mo.setNameMod(moduleQuery.getString(0));
                mo.setModNotes(moduleQuery.getString(3));
                mo.setCourseLeader(moduleQuery.getString(2));
                mo.setModuleCode(moduleQuery.getString(1));
                moduleList.add(mo);
            }
        }
        return moduleList;
    }

    public void updateAssignment(Assignment a) { // insert assignment to db
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists Assignments2(title TEXT, code TEXT, dueDate TEXT, notes TEXT, id INT, hID INT, tfID INT, feID INT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

        String insertStatement = "INSERT INTO Assignments2 VALUES('" + a.title + "','" + a.whichModuleIsTaskFor + "','" + a.dueDate + "','" + a.notes + "'," + a.assignmentId + "," + a.hourID + "," + a.tfHourId + "," + a.feHourId + ");";
        myBase.execSQL(insertStatement);
    }
    public void deleteAssignment(Assignment a) { // takes in an assignment
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists Assignments2(title TEXT, code TEXT, dueDate TEXT, notes TEXT, id INT, hID INT, tfID INT, feID INT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

        String deleteStatement = "DELETE FROM Assignments2 WHERE title = '" + a.title + "';"; // deletes it from the db
        myBase.execSQL(deleteStatement);
        startActivity(new Intent(editAssignment.this, Assignments.class)); // refreshes page

    }
}
