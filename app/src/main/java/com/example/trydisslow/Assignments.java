package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Assignments extends AppCompatActivity {
String selected;
    String selectedAssignment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assignments);
        getSupportActionBar().hide();

        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayList<String> listItems = new ArrayList<String>();

       SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
       myBase.execSQL("CREATE TABLE if not exists NEWASSIGNMENTSWITHIDS2(title TEXT, code TEXT, dueDate TEXT, notes TEXT, id INT, hID INT, tfID INT, feID INT);");
        Cursor query = myBase.rawQuery("SELECT * FROM NEWASSIGNMENTSWITHIDS2", null);

        if(query.moveToFirst()) {
            String name = query.getString(0) + "for module" + query.getString(1);
            listItems.add(name);
            while (query.moveToNext()) {
                name = query.getString(0);
                listItems.add(name);
            }
        }
//////        listItems.add("second");
////        listItems.add("third");
////        listItems.add("fourth");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Assignments.this,android.R.layout.simple_list_item_single_choice,listItems);
       myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
             selectedAssignment = myListView.getItemAtPosition(i).toString();
//  selectedModule = myModuleListView.getItemAtPosition(i).toString();
                Toast.makeText(Assignments.this, selected, Toast.LENGTH_LONG).show();
               // adapter.dismiss(); // If you want to close the adapter
           }
        });
//
//
//
        AppCompatButton modulesAndClassesButton = (AppCompatButton)findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, ModulesAndClasses.class));
            }
        });
//
//       AppCompatButton settingsButton =(AppCompatButton)findViewById(R.id.buttonSettings);
//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Assignments.this, Settings.class));
//            }
//        });
       AppCompatButton calendarButton =(AppCompatButton)findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, Calendar.class));
            }
        });
       AppCompatButton assignmentButton =(AppCompatButton)findViewById(R.id.buttonAssignments);
        assignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Assignments.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });

       AppCompatButton addAssignmentButton =(AppCompatButton)findViewById(R.id.buttonGoToAddAssignments);
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor moduleQuery = myBase.rawQuery("SELECT * FROM NEWMODULE3", null);

                if(moduleQuery.moveToFirst()) {
                    if (!moduleQuery.moveToNext()) {
                        Toast.makeText(Assignments.this, "You cannot add an assignment before adding a module", Toast.LENGTH_LONG).show();
                    } else {

                        startActivity(new Intent(Assignments.this, addAssignment.class));
                    }
                }

               // startActivity(new Intent(Assignments.this, addAssignment.class));
            }
        });
       AppCompatButton editAssignmentButton =(AppCompatButton)findViewById(R.id.buttonGoToEditAssignments);
        editAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAssignment== null) {
                    Toast.makeText(Assignments.this, "Please choose an assignment to edit", Toast.LENGTH_LONG).show();

                } else {
                    Intent i = new Intent(Assignments.this, editAssignment.class);
                    i.putExtra("editableAssignment", selectedAssignment);
                    startActivity(i);

                }
            }
        });
       AppCompatButton deleteAssignmentButton =(AppCompatButton)findViewById(R.id.deleteAssignmentButton);
        deleteAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAssignment== null) {
                    Toast.makeText(Assignments.this, "Please choose an assignment to delete", Toast.LENGTH_LONG).show();

                } else {
                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

                String retrieveDetails = "SELECT * FROM NEWASSIGNMENTSWITHIDS2 WHERE title = '" + selectedAssignment + "'";
                Cursor assignmentDetails = myBase.rawQuery(retrieveDetails, null);
                if (assignmentDetails.moveToFirst()) {
                    int hourId = assignmentDetails.getInt(5);
                    Toast.makeText(Assignments.this, Integer.toString(hourId), Toast.LENGTH_LONG).show();
                    int tfHourId = assignmentDetails.getInt(6);
                    int feHourId = assignmentDetails.getInt(7);
                    if (hourId > 0) {
                        Intent myIntent = new Intent(Assignments.this, Assignments.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(Assignments.this, hourId, myIntent, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);
                        Toast.makeText(Assignments.this, "cancelled", Toast.LENGTH_LONG).show();
                    }
                    if (tfHourId > 0) {
                        Intent myIntent = new Intent(Assignments.this, Assignments.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(Assignments.this, tfHourId, myIntent, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);
                    }
                    if (feHourId > 0) {
                        Intent myIntent = new Intent(Assignments.this, Assignments.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(Assignments.this, feHourId, myIntent, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);
                    }
                    Toast.makeText(Assignments.this, "all cancelled", Toast.LENGTH_LONG).show();
//                }
                    String deleteStatement = "DELETE FROM NEWASSIGNMENTSWITHIDS2 WHERE title = '" + selectedAssignment+ "';";
                    myBase.execSQL(deleteStatement);
                    startActivity(new Intent(Assignments.this, Assignments.class));

                }
            }}
        });
    }
}