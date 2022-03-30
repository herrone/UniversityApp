package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ModulesAndClasses extends AppCompatActivity {
String selectedModule;
String selectedClass;
int selectedCode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_and_classes);
        getSupportActionBar().hide();


      ListView myModuleListView = (ListView) findViewById(R.id.moduleListView);
        ArrayList<String> moduleListItems = new ArrayList<String>();
        ListView myClassListView = (ListView) findViewById(R.id.classListView);
        ArrayList<String> classListItems = new ArrayList<String>();

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists NEWMODULE3(title TEXT, code TEXT, leader TEXT, notes TEXT);");

        Cursor moduleQuery = myBase.rawQuery("SELECT * FROM NEWMODULE3", null);
//
        if(moduleQuery.moveToFirst()) {
            String name = moduleQuery.getString(0) + "(" + moduleQuery.getString(1) + ")" ;
           // moduleListItems.add(name);
            while (moduleQuery.moveToNext()) {
                name = moduleQuery.getString(0) + "/" + moduleQuery.getString(1) ;
                moduleListItems.add(name);
            }
        }
        ArrayAdapter<String> moduleAdapter = new ArrayAdapter<String>(ModulesAndClasses.this,R.layout.activity_module_list,moduleListItems);
        myModuleListView.setAdapter(moduleAdapter);
        myModuleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mine =  myModuleListView.getItemAtPosition(i).toString();
               String[] bits = mine.split("/");
                selectedModule = bits[0];

            }
        });
        myModuleListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myClassListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       // myClassListView.getSelectedItem();
//
        myBase.execSQL("CREATE TABLE if not exists NEWCLASSWITHIDS4(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT );");
        // String insertStatement = "INSERT INTO Classes2 VALUES('" + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "');";

        myBase.execSQL("CREATE TABLE if not exists NEWMODULE3(title TEXT, code TEXT, leader TEXT, notes TEXT);");
       // myBase.execSQL("CREATE TABLE if not exists NEWCLASSWITHIDS4(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, start TEXT, finish TEXT, day TEXT, id INT);");
         Cursor classQuery = myBase.rawQuery("SELECT NEWCLASSWITHIDS4.type, NEWMODULE3.code, title, day, start, finish, NEWCLASSWITHIDS4.id FROM NEWCLASSWITHIDS4 JOIN NEWMODULE3 ON NEWCLASSWITHIDS4.code = NEWMODULE3.code;", null);

        if(classQuery.moveToFirst()) {
            selectedCode = classQuery.getInt(6);
            String name =  classQuery.getString(2)+ " - " + classQuery.getString(0)  + " - Every " + classQuery.getString(3) + "-" + Integer.parseInt(classQuery.getString(6));
            classListItems.add(name);

            while (classQuery.moveToNext()) {
              //  name =  classQuery.getString(2)+ ",(" + classQuery.getString(1)+ ") - " + classQuery.getString(0)  + " Every " + classQuery.getString(3);
             //  name = classQuery.getString(0) + "," + classQuery.getString(2)+ ",(" + classQuery.getString(1)+ "), Every " + classQuery.getString(3)+ ", from " + classQuery.getString(4)+ ", to " + classQuery.getString(5)+ "," + classQuery.getInt(6);
            //    classListItems.add(name);
                name =  classQuery.getString(2)+ " - " + classQuery.getString(0)  + " - Every " + classQuery.getString(3) + "-" + Integer.parseInt(classQuery.getString(6));
                classListItems.add(name);
            }
        }
//
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(ModulesAndClasses.this,R.layout.activity_class_list,classListItems);
        myClassListView.setAdapter(classAdapter);
        myClassListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedClass = myClassListView.getItemAtPosition(i).toString();

            }
        });


       AppCompatButton addModuleButton =(AppCompatButton)findViewById(R.id.buttonGoToAddModule);
        addModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, addModule.class));
            }
        });
       AppCompatButton assignmentsButton =(AppCompatButton) findViewById(R.id.buttonAssignments);
        assignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, Assignments.class));
            }
        });
       AppCompatButton modulesAndClassesButton =(AppCompatButton) findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ModulesAndClasses.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });

       AppCompatButton calendarButton =(AppCompatButton) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, Calendar.class));
            }
        });
       AppCompatButton editModuleButton =(AppCompatButton)findViewById(R.id.editModuleButton);
        editModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedModule == null){
                    Toast.makeText(ModulesAndClasses.this, "Please choose a module to edit", Toast.LENGTH_LONG).show();

                }
                else {
                    Intent i = new Intent(ModulesAndClasses.this, editModule.class);
                    i.putExtra("editableModule", selectedModule);
                    startActivity(i);
                }
            }
        });
       AppCompatButton deleteModuleButton =(AppCompatButton)findViewById(R.id.deleteModuleButton);
        deleteModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedModule == null){
                    Toast.makeText(ModulesAndClasses.this, "Please choose a module to delete", Toast.LENGTH_LONG).show();

                }
                else {
                    String deleteStatement = "DELETE FROM NEWMODULE3 WHERE title = '" + selectedModule + "';";
                    myBase.execSQL(deleteStatement);
                    startActivity(new Intent(ModulesAndClasses.this, ModulesAndClasses.class));
                }
            }
        });

       AppCompatButton editClassButton =(AppCompatButton)findViewById(R.id.editClassButton);
        editClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedClass == null){
                    Toast.makeText(ModulesAndClasses.this, "Please choose a class to edit", Toast.LENGTH_LONG).show();

                }
                else {

                    Intent i = new Intent(ModulesAndClasses.this, editClass.class);
                    i.putExtra("editableClass", selectedClass);
                    startActivity(i);
                }
            }
        });
       AppCompatButton deleteClassButton =(AppCompatButton)findViewById(R.id.deleteClassButton);
        deleteClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedClass == null) {

                    Toast.makeText(ModulesAndClasses.this, "Please choose a class to delete", Toast.LENGTH_LONG).show();

                } else {
                    String[] bits = selectedClass.split("-");
                    String selectedCode = bits[3];
                   // Toast.makeText(ModulesAndClasses.this, selectedCode, Toast.LENGTH_LONG).show();
                    //String deleteTableStatement = "DROP TABLE NEWCLASSWITHIDS4";
                     String deleteStatement = "DELETE FROM NEWCLASSWITHIDS4 WHERE id = " + selectedCode + ";";
                    myBase.execSQL(deleteStatement);
                   // myBase.execSQL(deleteTableStatement);
                    startActivity(new Intent(ModulesAndClasses.this, ModulesAndClasses.class));
                }
            }
        });
        AppCompatButton addClassButton =(AppCompatButton)findViewById(R.id.buttonGoToAddClass2);
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // myBase.execSQL("CREATE TABLE if not exists NEWMODULE3(title TEXT, code TEXT, leader TEXT, notes TEXT);");

                Cursor moduleQuery = myBase.rawQuery("SELECT * FROM NEWMODULE3", null);

                if(moduleQuery.moveToFirst()) {
                    if (!moduleQuery.moveToNext()) {
                        Toast.makeText(ModulesAndClasses.this, "You cannot add a class before adding a module", Toast.LENGTH_LONG).show();
                    } else {

                        startActivity(new Intent(ModulesAndClasses.this, addClass.class));
                    }
                }
                }


        });


    }
}