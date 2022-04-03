package com.example.trydisslow;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;

public class editClass extends AppCompatActivity {
    String name;
    String code;
    String type;
    String professor;
    String notesText;
            String location ;
        String day ;
        String start;
        String finish ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String editableClass = intent.getExtras().getString("editableClass");

        Toast.makeText(this, editableClass,
                Toast.LENGTH_SHORT).show();
        EditText notes = (EditText) findViewById(R.id.addNotesBox);
        TimePicker from = (TimePicker) findViewById(R.id.timePickerFrom);
        TimePicker to = (TimePicker) findViewById(R.id.timePickerTo);
        EditText lecturer = (EditText) findViewById(R.id.lecturerAddBox);
        EditText where = (EditText) findViewById(R.id.addLocationBox);
        Spinner moduleCodeList = findViewById(R.id.moduleCodeListGrades);
        Spinner dayList = findViewById(R.id.dayList);
        Spinner classTypeList = findViewById(R.id.classTypeList);
        ArrayList<String> moduleCodeArray = new ArrayList<String>();

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        String retrieveClass = "SELECT * FROM NEWCLASSWITHIDS WHERE title = '" + editableClass + "'";
        //code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT
        Cursor query = myBase.rawQuery(retrieveClass, null);
      //  SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        Cursor queryModuleCodes = myBase.rawQuery("SELECT * FROM NEWMODULE3", null);

        if(query.moveToFirst()) {
            while (query.moveToNext()) {
                moduleCodeArray.add(query.getString(0));
            }
        }

//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.

        ArrayAdapter<String> moduleCodeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);

        if (query.moveToFirst()) {
           // code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT
            code = query.getString(0);
             type= query.getString(1);
             professor = query.getString(2);
             notesText = query.getString(3);
             location = query.getString(4);
             day = query.getString(5);
            start= query.getString(6);
            finish = query.getString(7);
           notes.setText(notesText);
            lecturer.setText(professor);
            where.setText(location);


        }

        //from.set

//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.

        //ArrayAdapter<String> moduleCodeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);
        String[] classTypeItems = new String[]{"choose class type", "Lecture", "Practical", "Tutorial"};
        ArrayAdapter<String> classTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classTypeItems);
//set the spinners adapter to the previously created one.
        classTypeList.setAdapter(classTypeAdapter);
        moduleCodeList.setAdapter(moduleCodeAdapter);
        //Spinner dayList = findViewById(R.id.dayList);
//create a list of items for the spinner.
        String[] days = new String[]{"choose day", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days);
        dayList.setAdapter(adapterDays);
        AppCompatButton saveClassButton = (AppCompatButton) findViewById(R.id.buttonSaveClass);
        saveClassButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String deleteStatement = "DELETE FROM NEWCLASS2 WHERE code = '" + code + "' AND type = '" + type + "'";
                myBase.execSQL(deleteStatement);
                Class c = new Class();
                c.modCode = moduleCodeList.getSelectedItem().toString();
                c.notes = notes.getText().toString();
                c.lecturer = lecturer.getText().toString();
                c.locationOrLink = where.getText().toString();
                c.dayOfClass = dayList.getSelectedItem().toString();

                //frommer.second = 0;

                // toer.second = 0;
                c.startTime = from.toString();
                c.endTime = to.toString();
                c.classType = classTypeList.getSelectedItem().toString();
                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

//                String dayOfClass;
//                Time startTime;
//                Time endTime;


                //myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TIME, finish TIME );");
                myBase.execSQL("CREATE TABLE if not exists NEWCLASSWITHIDS4(code TEXT, type TEXT, day TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT );");
                // String insertStatement = "INSERT INTO Classes2 VALUES('" + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "');";
                String insertStatement = "INSERT INTO NEWCLASSWITHIDS4 VALUES('" + c.modCode + "','" + c.classType + "','" + c.dayOfClass + "','" + c.lecturer + "','" + c.notes + "','" + c.locationOrLink + "','" + c.dayOfClass + "','" + c.startTime + "','" + c.endTime + "'," +c.id + ");";
                myBase.execSQL(insertStatement);
                Toast.makeText(editClass.this, c.classType + c.modCode + c.lecturer + c.notes + c.dayOfClass + c.locationOrLink + "Saved Class for module ",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(editClass.this, ModulesAndClasses.class));

            }
        });


        // TimePicker picker=(TimePicker)findViewById(R.id.timePickerFrom);
        // from.setIs24HourView(true);
        // to.setIs24HourView(true);
    }
}
