package com.example.trydisslow;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

public class editAssignment extends AppCompatActivity {
    String name;
    String notes;
    String code;
    String dueD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);
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
        String retrieveDetails = "SELECT * FROM NEWASSIGNMENT3 WHERE title = '" + editableAssignment + "'";
        Cursor assignmentDetails = myBase.rawQuery(retrieveDetails, null);

        if (assignmentDetails.moveToFirst()) {
            name = assignmentDetails.getString(0);
         code = assignmentDetails.getString(1);
            dueD = assignmentDetails.getString(2);
            notes = assignmentDetails.getString(3);

        }
        assignmentNotes.setText(notes);
        assignmentTitle.setText(name);
        moduleCodeArray.add(code);
        ArrayAdapter<String> moduleCodeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);

        // ArrayAdapter<String> moduleCodeArray = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodes);
//set the spinners adapter to the previously created one.
        moduleCodeList.setAdapter(moduleCodeAdapter);

        Button saveAssignmentButton = (Button) findViewById(R.id.buttonSaveAssignment);
        saveAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
               String deleteStatement = "DELETE FROM NEWASSIGNMENT3 WHERE title = '" + editableAssignment + "'";
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
                Assignment a = new Assignment(title, d.toString(), moduleCodeInQuestion, notes);
                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

                myBase.execSQL("CREATE TABLE if not exists NEWASSIGNMENT3(title TEXT, code TEXT, dueDate TEXT, notes TEXT);");
                String insertStatement = "INSERT INTO NEWASSIGNMENT3 VALUES('" + a.title + "','" + a.whichModuleIsTaskFor + "','" + a.dueDate + "','" + a.notes + "');";
                // String insertStatement = "INSERT INTO Modules VALUES('" + m.nameMod + "','"+ m.moduleCode + "','"  + m.courseLeader + "','"  + m.modNotes + "')\"";
                myBase.execSQL(insertStatement);
                Toast.makeText(editAssignment.this, "Time of " + a.dueDate,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(editAssignment.this, Assignments.class));

            }
        });
        Button modulesAndClassesButton = (Button)findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(editAssignment.this, ModulesAndClasses.class));
            }
        });
        Button assignmentsButton = (Button) findViewById(R.id.buttonAssignments);
        assignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(editAssignment.this, Assignments.class));
            }
        });


        Button settingsButton = (Button)findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(editAssignment.this, Settings.class));
            }
        });
        Button calendarButton = (Button) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(editAssignment.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });
    }
}
//       }
