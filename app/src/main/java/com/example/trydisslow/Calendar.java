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
import java.util.HashMap;
import java.util.List;

public class Calendar extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().hide();
        List<Object> mixedList = null;
        ListView lvAssignments = (ListView) findViewById(R.id.lv);

        ListView lv = (ListView) findViewById(R.id.lv2);
        ArrayList<Assignment> storedTasks = new ArrayList<Assignment>();
        ArrayList<String> listItems = new ArrayList<String>();
        ArrayList<String> classListItems = new ArrayList<String>();

        HashMap<String, Date> calendarItems  = new HashMap<>();

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
       //myBase.execSQL("DROP TABLE NEWCLASSWITHIDS4");

        myBase.execSQL("CREATE TABLE if not exists NEWASSIGNMENTSWITHIDS2(title TEXT, code TEXT, dueDate TEXT, notes TEXT, id INT, hID INT, tfID INT, feID INT);");
        Cursor query = myBase.rawQuery("SELECT * FROM NEWASSIGNMENTSWITHIDS2", null);
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
            storedTasks.add(a);

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
                storedTasks.add(a);
            }
        }

        storedTasks.sort(Comparator.comparing(o -> o.dueDate));

//listItems.add("Assignments");

        for (Assignment c : storedTasks) {
            c.dueDate.getDate();
            c.dueDate.getMonth();
            String dater = c.dueDate.getDate() + "/" + c.dueDate.getMonth();
            String printable = String.format("  %s for %s, due on %s \n", c.title, c.whichModuleIsTaskFor, dater);
            //HashMap<String, Date> calendarItems  = new HashMap<>();
           // calendarItems.put(printable, c.dueDate);
            listItems.add(printable);
        }
        ArrayAdapter<String> adapterAssignments = new ArrayAdapter<String>(Calendar.this, R.layout.activity_calendar_list, listItems);
        lvAssignments.setAdapter(adapterAssignments);

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

  Date firstDate = new Date();
        // Toast.makeText(getApplicationContext(), firstDate.toString(), Toast. LENGTH_LONG).show();
        Date secondDate = new Date();
       // myBase.execSQL("DROP DATABASE Names.db;");
        myBase.execSQL("CREATE TABLE if not exists NEWCLASSWITHIDS4(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        Cursor classQuery = myBase.rawQuery("SELECT * FROM NEWCLASSWITHIDS4", null);
        ArrayList<Class> classes = new ArrayList<Class>();
        ArrayList<PrintableClass> printClasses = new ArrayList<PrintableClass>();

        if (classQuery.moveToFirst()) {
            String code = classQuery.getString(0);
            String type = classQuery.getString(1);
            String lecturer = classQuery.getString(2);
            String notes = classQuery.getString(3);
            String location = classQuery.getString(4);
            String day = classQuery.getString(5);
            String start = classQuery.getString(6);
            String finish = classQuery.getString(7);
            int id = classQuery.getInt(8);
            Class c = new Class(code, type, day, start, finish, location, notes, lecturer, id);
           classes.add(c);

            while (classQuery.moveToNext()) {
                code = classQuery.getString(0);
               type = classQuery.getString(1);
                lecturer = classQuery.getString(2);
                 notes = classQuery.getString(3);
                 location = classQuery.getString(4);
                 day = classQuery.getString(5);
                 start = classQuery.getString(6);
                 finish = classQuery.getString(7);
                 id = classQuery.getInt(8);
                 c = new Class(code, type, day, start, finish, location, notes, lecturer, id);
                classes.add(c);
            }
        }


        String myday = "";
        secondDate.setYear(firstDate.getYear() + 1);
        List<Date> myList = getDaysBetweenDates(firstDate, secondDate);
        for (Class c :
                classes) {

            for (Date d : myList
            ) {

                int mydaynum = d.getDay();

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
                if (c.dayOfClass.equals(myday)) {
              PrintableClass pc = new PrintableClass(c, d);
                    printClasses.add(pc);

                }
            }
        }
        printClasses.sort(Comparator.comparing(o -> o.dueDate));

       // listItems.add("Classes");
        for (PrintableClass c : printClasses) {
            c.dueDate.getDate();
            c.dueDate.getMonth();
            String dater = c.dueDate.getDate() + "/" + c.dueDate.getMonth();
           String printable = String.format("  %s for %s on \n  %s, from %s to %s", c.contained.classType, c.contained.modCode, dater, c.contained.startTime, c.contained.endTime);
           classListItems.add(printable);
            //  name = classQuery.getString(0) + "," + classQuery.getString(2)+ ",(" + classQuery.getString(1)+ "), Every " + classQuery.getString(3)+ ", from " + classQuery.getString(4)+ ", to " + classQuery.getString(5)+ "," + classQuery.getInt(6);
           // calendarItems.put(printable, c.dueDate);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Calendar.this, R.layout.activity_calendar_list2, classListItems);
        lv.setAdapter(adapter);

    }

    public static List<Date> getDaysBetweenDates(Date startDate, Date endDate){
        ArrayList<Date> dates = new ArrayList<Date>();
        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        cal1.setTime(startDate);

        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal2.setTime(endDate);

        while(cal1.before(cal2) || cal1.equals(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(java.util.Calendar.DATE, 1);
        }
        return dates;
    }

   }
