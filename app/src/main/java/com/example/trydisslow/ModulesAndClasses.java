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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_and_classes);
        getSupportActionBar().hide();
       AppCompatButton addClassButton =(AppCompatButton)findViewById(R.id.buttonGoToAddClass2);
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, addClass.class));

            }
        });
      ListView myModuleListView = (ListView) findViewById(R.id.moduleListView);
        ArrayList<String> moduleListItems = new ArrayList<String>();
        ListView myClassListView = (ListView) findViewById(R.id.classListView);
        ArrayList<String> classListItems = new ArrayList<String>();

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists NEWMODULE3(title TEXT, code TEXT, leader TEXT, notes TEXT);");

        Cursor moduleQuery = myBase.rawQuery("SELECT * FROM NEWMODULE3", null);
//
//        moduleListItems.add("a");
//        moduleListItems.add("a");
//        moduleListItems.add("a");
//        moduleListItems.add("a");
//        moduleListItems.add("a");
//        moduleListItems.add("a");
        if(moduleQuery.moveToFirst()) {
            String name = moduleQuery.getString(0);
           // moduleListItems.add(name);
            while (moduleQuery.moveToNext()) {
                name = moduleQuery.getString(0);
                moduleListItems.add(name);
            }
        }
        ArrayAdapter<String> moduleAdapter = new ArrayAdapter<String>(ModulesAndClasses.this,R.layout.activity_module_list,moduleListItems);
        myModuleListView.setAdapter(moduleAdapter);
        myModuleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectedModule = myModuleListView.getItemAtPosition(i).toString();

                //    Toast.makeText(Assignments.this, s, Toast.LENGTH_LONG).show();
                // adapter.dismiss(); // If you want to close the adapter
            }
        });
        myModuleListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myClassListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       // myClassListView.getSelectedItem();
//
        myBase.execSQL("CREATE TABLE if not exists NEWCLASSWITHIDS(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
         Cursor classQuery = myBase.rawQuery("SELECT * FROM NEWCLASSWITHIDS", null);

        if(classQuery.moveToFirst()) {
            String name = classQuery.getString(8) + "," + classQuery.getString(1);
            classListItems.add(name);
            while (classQuery.moveToNext()) {
                name = classQuery.getString(8) + "," + classQuery.getString(1);
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

//
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
//
//
       AppCompatButton calendarButton =(AppCompatButton) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, Calendar.class));
            }
        });
//       AppCompatButton editModuleButton =(AppCompatButton)findViewById(R.id.editModuleButton);
//        editModuleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent (ModulesAndClasses.this, editModule.class);
//                i.putExtra("editableModule", selectedModule);
//                startActivity(i);
//            }
//        });
//       AppCompatButton deleteModuleButton =(AppCompatButton)findViewById(R.id.deleteModuleButton);
//        deleteModuleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String deleteStatement = "DELETE FROM NEWMODULE3 WHERE title = '" + selectedModule + "';";
//                myBase.execSQL(deleteStatement);
//                startActivity(new Intent(ModulesAndClasses.this, ModulesAndClasses.class));
//
//            }
//        });
//
//       AppCompatButton editClassButton =(AppCompatButton)findViewById(R.id.editClassButton);
//        editClassButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent (ModulesAndClasses.this, editClass.class);
//                i.putExtra("editableClass", selectedClass);
//                startActivity(i);
//            }
//        });
//       AppCompatButton deleteClassButton =(AppCompatButton)findViewById(R.id.deleteClassButton);
//        deleteClassButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              String[] bits =  selectedClass.split(",");
//             int selectedCode = Integer.parseInt(bits[0]);
//                String deleteStatement = "DELETE FROM NEWCLASSWITHIDS WHERE id = " + selectedCode+ ";";
//                myBase.execSQL(deleteStatement);
//                startActivity(new Intent(ModulesAndClasses.this, ModulesAndClasses.class));
//            }
//        });


    }
}