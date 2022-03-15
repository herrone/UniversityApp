package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        TextView cal = (TextView)findViewById(R.id.classBox1);
        Button modulesAndClassesButton = (Button)findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, ModulesAndClasses.class));
            }
        });
        Button assignmentsButton = (Button) findViewById(R.id.buttonAssignments);
        assignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, Assignments.class));
            }
        });


        Button settingsButton = (Button)findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calendar.this, Settings.class));
            }
        });
        Button calendarButton = (Button) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Calendar.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });
    }
}