package com.example.trydisslow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Calendar extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().hide();
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists Assignments2(title TEXT, code TEXT, dueDate TEXT, notes TEXT, id INT, hID INT, tfID INT, feID INT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

        ListView lvAssignments = (ListView) findViewById(R.id.lv);

        ListView lv = (ListView) findViewById(R.id.lv2);
        ArrayList < String > listItems = new ArrayList < String > ();
        ArrayList < String > classListItems = new ArrayList < String > ();
        ArrayList < Assignment > storedTasks = getAllAssignments(); // get all assignments

        if (storedTasks.size() > 2) { //  if there is more than 1
            storedTasks.sort(Comparator.comparing(o -> o.dueDate)); // sort them by date
        }
        for (Assignment c: storedTasks) { // for each assignment in the list
            c.dueDate.getMonth();
            String dater = c.dueDate.getDate() + "/" + c.dueDate.getMonth();
            String printable = String.format("%s for %s, due on %s \n", c.title, c.whichModuleIsTaskFor, dater);
            listItems.add(printable); // format a string and make it nice, add it to the list to be displaued
        }
        //
        ArrayAdapter < String > adapterAssignments = new ArrayAdapter < String > (Calendar.this, R.layout.activity_calendar_list, listItems);
        lvAssignments.setAdapter(adapterAssignments);

        Date firstDate = new Date(); // take todays date
        Date secondDate = new Date();
        ArrayList < Class > classes = getAllClasses();
        ArrayList < PrintableClass > printClasses = new ArrayList < PrintableClass > ();
        String myday = "";
        secondDate.setYear(firstDate.getYear() + 1);  // and one year from now
        List < Date > myList = getDaysBetweenDates(firstDate, secondDate); // get a list of every date
        for (Class c:
                classes) { // for each class in sysetm

            for (Date d: myList) { // for each date between today and in a year

                int mydaynum = d.getDay(); // get each day

                if (mydaynum == 0) {
                    myday = "Sunday";
                } else if (mydaynum == 1) {
                    myday = "Monday";
                } else if (mydaynum == 2) {
                    myday = "Tuesday";
                } else if (mydaynum == 3) {
                    myday = "Wednesday";
                } else if (mydaynum == 4) {
                    myday = "Thursday";
                } else if (mydaynum == 5) {
                    myday = "Friday";
                } else if (mydaynum == 6) {
                    myday = "Saturday";
                }
                if (c.dayOfClass.equals(myday)) { // if the class' day is that day
                    PrintableClass pc = new PrintableClass(c, d); // add its date
                    printClasses.add(pc); // add to lisst
                    //eventually we will have a fully formed list of classes and dates of classes

                }
            }
        }
        printClasses.sort(Comparator.comparing(o -> o.dueDate)); // sort it by date

        for (PrintableClass c: printClasses) {
            c.dueDate.getDate();
            c.dueDate.getMonth();
            String dater = c.dueDate.getDate() + "/" + c.dueDate.getMonth(); // format it into a nice string
            String printable = String.format("%s for %s on \n  %s, from %s to %s", c.contained.classType, c.contained.modCode, dater, c.contained.startTime, c.contained.endTime);
            classListItems.add(printable); // add it on

        }

        ArrayAdapter < String > adapter = new ArrayAdapter < String > (Calendar.this, R.layout.activity_calendar_list2, classListItems);
        lv.setAdapter(adapter); // populate listview with the list opf classes and their dates (printable classes)


        //bottom buttons
        AppCompatButton modulesAndClassesButton = (AppCompatButton) findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, ModulesAndClasses.class));
            }
        });
        AppCompatButton assignmentsButton = (AppCompatButton) findViewById(R.id.buttonAssignments);
        assignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, Assignments.class));
            }
        });


        AppCompatButton calendarButton = (AppCompatButton) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Calendar.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static List < Date > getDaysBetweenDates(Date startDate, Date endDate) {// returns a full list of dates
        ArrayList < Date > dates = new ArrayList < Date > ();
        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        cal1.setTime(startDate);

        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal2.setTime(endDate);

        while (cal1.before(cal2) || cal1.equals(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(java.util.Calendar.DATE, 1);
        }
        return dates;
    }
    public ArrayList < Assignment > getAllAssignments() { // this method returns a full arraylist of assignments in the db
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        ArrayList<Assignment> assignmentList = new ArrayList<>();
        Cursor query = myBase.rawQuery("SELECT * FROM Assignments2", null); // select everything
        if (query.moveToFirst()) { // if its not empty
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
            Assignment a = new Assignment(name, dueDate, code, notes, id, hId, tfId, feId); //make and populate an object
            assignmentList.add(a); // add it to the list

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
                Assignment b = new Assignment(name, dueDate, code, notes, id, hId, tfId, feId);
                assignmentList.add(b); // continue until no more left
            }
        }

        return assignmentList; // return the list
    }

    public ArrayList < Class > getAllClasses() { // method returns a list of every class in db

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        ArrayList < Class > classList = new ArrayList < > (); // make new lass
        Class c = new Class();
        Class co = new Class();
        Cursor classQuery = myBase.rawQuery("SELECT * FROM Classes;", null); // select everything

        if (classQuery.moveToFirst()) { // if its not empty
            co.setId(classQuery.getInt(8));  // make and populate an object
            co.setLecturer(classQuery.getString(2));
            co.setNotes(classQuery.getString(3));
            co.setModCode(classQuery.getString(0));
            co.setEndTime(classQuery.getString(7));
            co.setStartTime(classQuery.getString(6));
            co.setLocationOrLink(classQuery.getString(4));
            co.setClassType(classQuery.getString(1));
            co.setDayOfClass(classQuery.getString(5));
            classList.add(co); // add it to list

            while (classQuery.moveToNext()) { // while it's still not empty
                c.setId(classQuery.getInt(8));
                c.setLecturer(classQuery.getString(2));
                c.setNotes(classQuery.getString(3));
                c.setModCode(classQuery.getString(0));
                c.setEndTime(classQuery.getString(7));
                c.setStartTime(classQuery.getString(6));
                c.setLocationOrLink(classQuery.getString(4)); // populate new object
                c.setDayOfClass(classQuery.getString(5));
                classList.add(c); // add it to the list
            }
        }
        return classList; // return list
    }


}