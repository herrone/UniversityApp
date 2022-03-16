package com.example.trydisslow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class addClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        EditText notes = (EditText) findViewById(R.id.addNotesBox);
        TimePicker from = (TimePicker) findViewById(R.id.timePickerFrom);
        TimePicker to = (TimePicker) findViewById(R.id.timePickerTo);
        EditText lecturer = (EditText) findViewById(R.id.lecturerAddBox);
        EditText where = (EditText) findViewById(R.id.addLocationBox);
        Spinner moduleCodeList = findViewById(R.id.moduleCodeList);
        Spinner dayList = findViewById(R.id.dayList);
        Spinner classTypeList = findViewById(R.id.classTypeList);
        ArrayList<String> moduleCodeArray = new ArrayList<String>();



     //  moduleCodeArray[0] = "choose a module";

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        Cursor query = myBase.rawQuery("SELECT * FROM NEWMODULE8", null);

        if(query.moveToFirst()) {
            while (query.moveToNext()) {
                moduleCodeArray.add(query.getString(0));
            }
        }




//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.

        ArrayAdapter<String> moduleCodeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);

        String[] classTypeItems = new String[]{"choose class type", "lecture", "practical", "tutorial"};
        ArrayAdapter<String> classTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classTypeItems);
//set the spinners adapter to the previously created one.
        classTypeList.setAdapter(classTypeAdapter);
        moduleCodeList.setAdapter(moduleCodeAdapter);
        //Spinner dayList = findViewById(R.id.dayList);
//create a list of items for the spinner.
        String[] days = new String[]{"choose day", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days);
        dayList.setAdapter(adapterDays);
        Button saveClassButton = (Button) findViewById(R.id.buttonSaveClass);
        saveClassButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
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
                myBase.execSQL("CREATE TABLE if not exists NEWCLASS2(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT );");
               // String insertStatement = "INSERT INTO Classes2 VALUES('" + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "');";
                 String insertStatement = "INSERT INTO NEWCLASS2 VALUES('" + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "','" + c.locationOrLink + "','" + c.startTime + "','" + c.dayOfClass + "','"+ c.endTime + "');";
                myBase.execSQL(insertStatement);
                Toast.makeText(addClass.this, c.classType +  c.modCode + c.lecturer + c.notes  + c.dayOfClass + c.locationOrLink + "Saved Class for module ",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(addClass.this, ModulesAndClasses.class));

            }
        });


       // TimePicker picker=(TimePicker)findViewById(R.id.timePickerFrom);
       // from.setIs24HourView(true);
       // to.setIs24HourView(true);
    }
}