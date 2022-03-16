package com.example.trydisslow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.util.Date;

public class addAssignment extends AppCompatActivity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
      //  Assignment a = new Assignment();
        Spinner moduleCodeList = findViewById(R.id.moduleCodeList);
        String [] moduleCodes = {"a", "b", "c"};
        ArrayAdapter<String> moduleCodeArray = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodes);
//set the spinners adapter to the previously created one.
        moduleCodeList.setAdapter(moduleCodeArray);
        EditText assignmentTitle = (EditText)findViewById(R.id.assignmentTitleInput);
        EditText assignmentNotes = (EditText)findViewById(R.id.addAssignmentNotesBox);
        TimePicker timeDue = (TimePicker) findViewById(R.id.timeDue);
        DatePicker dateDue = (DatePicker) findViewById(R.id.dateDue);

        Button saveAssignmentButton = (Button)findViewById(R.id.buttonSaveAssignment);
        saveAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

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
                Assignment a = new Assignment(title, d.toString(),  moduleCodeInQuestion,notes);
                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

                myBase.execSQL("CREATE TABLE if not exists NEWASSIGNMENT(title TEXT, code TEXT, dueDate TEXT, notes TEXT);");
                String insertStatement = "INSERT INTO NEWASSIGNMENT VALUES('" + a.title + "','" + a.whichModuleIsTaskFor + "','" + a.dueDate + "','" + a.notes + "');";
                // String insertStatement = "INSERT INTO Modules VALUES('" + m.nameMod + "','"+ m.moduleCode + "','"  + m.courseLeader + "','"  + m.modNotes + "')\"";
                myBase.execSQL(insertStatement);
                Toast.makeText(addAssignment.this, "Saved Assignment " + a.title,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(addAssignment.this, Assignments.class));

            }
            });
    }
}