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

public class ModulesAndClasses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_and_classes);
        TextView classA = (TextView)findViewById(R.id.classBox1);
        TextView classB = (TextView)findViewById(R.id.classBox2);
        TextView classC = (TextView)findViewById(R.id.classBox3);
        TextView moduleA = (TextView)findViewById(R.id.moduleBox1);
        TextView moduleB = (TextView)findViewById(R.id.moduleBox2);
        TextView moduleC = (TextView)findViewById(R.id.moduleBox3);
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        Cursor query = myBase.rawQuery("SELECT * FROM FullModule", null);
        Cursor query2 = myBase.rawQuery("SELECT * FROM ClassesFullStrings2", null);

        if(query.moveToFirst()){

            String name = query.getString(0);
            String name2 = query.getString(1);
            String name3 = query.getString(2);
            moduleA.setText(name);
            moduleB.setText(name2);
            moduleC.setText(name3);

        }
        else {
            Toast t = Toast.makeText(getApplicationContext()
                    , "nope", Toast.LENGTH_LONG);
            t.show();
        }
        if(query2.moveToFirst()){

            String name = query2.getString(0);
            String name2 = query2.getString(1);
            String name3 = query2.getString(2);
            classA.setText(name);
            classB.setText(name2);
            classC.setText(name3);

        }
        else {
            Toast t = Toast.makeText(getApplicationContext()
                    , "nope", Toast.LENGTH_LONG);
            t.show();
        }

        Button addClassButton = (Button)findViewById(R.id.buttonGoToAddClass);
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, addClass.class));
            }
        });
        Button addModuleButton = (Button)findViewById(R.id.buttonGoToAddModule);
        addModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, addModule.class));
            }
        });
        Button assignmentsButton = (Button) findViewById(R.id.buttonAssignments);
        assignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, Assignments.class));
            }
        });
        Button modulesAndClassesButton = (Button) findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ModulesAndClasses.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });

        Button settingsButton = (Button)findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, Settings.class));
            }
        });
        Button calendarButton = (Button) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, Calendar.class));
            }
        });


    }
}