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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addModule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_module2);
        //DatabaseSingleton db = new DatabaseSingleton();
        Module m = new Module();
        EditText moduleTitle = (EditText) findViewById(R.id.addModuleTitleBox);
       moduleTitle.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
              moduleTitle.setText("");
                return false;
            }
        });
        EditText moduleCod = (EditText) findViewById(R.id.addModuleCodeBox);
        moduleCod.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                moduleCod.setText("");
                return false;
            }
        });
        EditText moduleLeader = (EditText) findViewById(R.id.addModuleLeader);
        moduleLeader.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                moduleLeader.setText("");
                return false;
            }
        });
        EditText moduleNotes = (EditText) findViewById(R.id.addModuleNotesBox);
        moduleNotes.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                moduleNotes.setText("");
                return false;
            }
        });
        AppCompatButton saveModuleButton = (AppCompatButton) findViewById(R.id.buttonSaveModule);
        saveModuleButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                //EditText moduleTitle = (EditText) findViewById(R.id.addModuleTitleBox);
                m.nameMod = moduleTitle.getText().toString();
               // EditText moduleCod = (EditText) findViewById(R.id.addModuleCodeBox);
                m.moduleCode = moduleCod.getText().toString();
               // EditText moduleLeader = (EditText) findViewById(R.id.addModuleLeader);
                m.courseLeader = moduleLeader.getText().toString();
                //EditText moduleNotes = (EditText) findViewById(R.id.addModuleNotesBox);
                m.modNotes  = moduleNotes.getText().toString();

               // m.colour = "yellow";
                //DatabaseSingleton db = DatabaseSingleton.getInstance();

                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

                myBase.execSQL("CREATE TABLE if not exists NEWMODULE3(title TEXT, code TEXT, leader TEXT, notes TEXT);");
                String insertStatement = "INSERT INTO NEWMODULE3 VALUES('" + m.nameMod + "','" + m.moduleCode + "','" + m.courseLeader + "','" + m.modNotes + "')";
                // String insertStatement = "INSERT INTO Modules VALUES('" + m.nameMod + "','"+ m.moduleCode + "','"  + m.courseLeader + "','"  + m.modNotes + "')\"";
                myBase.execSQL(insertStatement);
                //db.addModule(m);
                Toast.makeText(addModule.this, "Saved Module " + m.nameMod,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(addModule.this, ModulesAndClasses.class));
            }
        });



    }

}