package com.example.trydisslow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addModule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_module2);
        Module m = new Module();
        EditText moduleTitle = (EditText) findViewById(R.id.addModuleTitleBox);
        moduleTitle.setOnTouchListener(new View.OnTouchListener() { // if box is touched, remove prompt
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                moduleTitle.setText("");
                return false;
            }
        });
        EditText moduleCod = (EditText) findViewById(R.id.addModuleCodeBox);  // if box is touched, remove prompt
        moduleCod.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                moduleCod.setText("");
                return false;
            }
        });
        EditText moduleLeader = (EditText) findViewById(R.id.addModuleLeader);  // if box is touched, remove prompt
        moduleLeader.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                moduleLeader.setText("");
                return false;
            }
        });
        EditText moduleNotes = (EditText) findViewById(R.id.addModuleNotesBox);  // if box is touched, remove prompt
        moduleNotes.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                moduleNotes.setText("");
                return false;
            }
        });
        AppCompatButton saveModuleButton = (AppCompatButton) findViewById(R.id.buttonSaveModule); // if box is touched
        saveModuleButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) { // create and populate object

                m.nameMod = moduleTitle.getText().toString();
                m.moduleCode = moduleCod.getText().toString();
                m.courseLeader = moduleLeader.getText().toString();
                m.modNotes = moduleNotes.getText().toString();

                addModule(m); // add it to the database

                Toast.makeText(addModule.this, "Saved Module " + m.nameMod,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(addModule.this, ModulesAndClasses.class));
            } // back to list
        });
    }

    public void addModule(Module m) { // this method takes a module
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists Assignments(title TEXT, module TEXT, date TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

        String insertStatement = "INSERT INTO Modules VALUES('" + m.nameMod + "','" + m.moduleCode + "','" + m.courseLeader + "','" + m.modNotes + "')";
        myBase.execSQL(insertStatement); // then adds it into db

    }


}