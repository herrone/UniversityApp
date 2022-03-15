package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Assignments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        TextView assignmentA = (TextView)findViewById(R.id.assignmentA);
        TextView assignmentB = (TextView)findViewById(R.id.assignmentB);
        TextView assignmentC = (TextView)findViewById(R.id.assignmentC);
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        Cursor query = myBase.rawQuery("SELECT * FROM FullAssignment", null);
      //  Cursor query2 = myBase.rawQuery("SELECT * FROM ClassesFullStrings2", null);

        if(query.moveToFirst()){

            String name = query.getString(0);
            String name2 = query.getString(1);
            String name3 = query.getString(2);
            assignmentA.setText(name);
            assignmentB.setText(name2);
            assignmentC.setText(name3);

        }

        else {
            Toast t = Toast.makeText(getApplicationContext()
                    , "nope", Toast.LENGTH_LONG);
            t.show();
        }
        Button modulesAndClassesButton = (Button)findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, ModulesAndClasses.class));
            }
        });

        Button settingsButton = (Button)findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, Settings.class));
            }
        });
        Button calendarButton = (Button)findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, Calendar.class));
            }
        });
        Button assignmentButton = (Button)findViewById(R.id.buttonAssignments);
        assignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Assignments.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });


        Button addAssignmentButton = (Button)findViewById(R.id.buttonGoToAddAssignments);
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, addAssignment.class));

               // startActivity(new Intent(Assignments.this, addAssignment.class));
            }
        });
    }
}