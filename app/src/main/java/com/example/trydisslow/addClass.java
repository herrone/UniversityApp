package com.example.trydisslow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class addClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_class);
        EditText notes = (EditText) findViewById(R.id.addNotesBox);
        notes.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notes.setText("");
                return false;
            }
        });
        TimePicker from = (TimePicker) findViewById(R.id.timePickerFrom);
        TimePicker to = (TimePicker) findViewById(R.id.timePickerTo);
        EditText lecturer = (EditText) findViewById(R.id.lecturerAddBox);
        lecturer.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               lecturer.setText("");
                return false;
            }
        });
        EditText where = (EditText) findViewById(R.id.addLocationBox);
        where.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                where.setText("");
                return false;
            }
        });
        Spinner moduleCodeList = findViewById(R.id.moduleCodeListGrades);
        Spinner dayList = findViewById(R.id.dayList);
        Spinner classTypeList = findViewById(R.id.classTypeList);
        ArrayList<String> moduleCodeArray = new ArrayList<String>();



     //  moduleCodeArray[0] = "choose a module";

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        Cursor query = myBase.rawQuery("SELECT * FROM NEWMODULE3", null);

        if(query.moveToFirst()) {
            while (query.moveToNext()) {
                moduleCodeArray.add(query.getString(1));
            }
        }




//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.

        ArrayAdapter<String> moduleCodeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);

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
        Random r = new Random();
      AppCompatButton saveClassButton =(AppCompatButton) findViewById(R.id.buttonSaveClass);
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
                c.id = r.nextInt(1000);

                //frommer.second = 0;
               // toer.second = 0;
               int fromHour = from.getHour();
                int fromMinute = from.getMinute();
                if(fromMinute<10){
                    c.startTime = fromHour + ":0" + fromMinute;
                }
                else{
                    c.startTime = fromHour + ":" + fromMinute;

                }
                int toHour = to.getHour();
                int toMinute = to.getMinute();
                if(toMinute<10){
                    c.startTime = toHour + ":0" + toMinute;
                }
                else{
                    c.startTime = toHour + ":" + toMinute;

                }
                c.startTime = fromHour + ":" + fromMinute;
                c.endTime =  toHour + ":" + toMinute;
                c.classType = classTypeList.getSelectedItem().toString();
                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

//                String dayOfClass;
//                Time startTime;
//                Time endTime;


                //myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TIME, finish TIME );");
                myBase.execSQL("CREATE TABLE if not exists NEWCLASSWITHIDS4(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT );");
               // String insertStatement = "INSERT INTO Classes2 VALUES('" + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "');";
                 String insertStatement = "INSERT INTO NEWCLASSWITHIDS4 VALUES('" + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "','" + c.locationOrLink+ "','" + c.dayOfClass + "','" +  c.startTime + "','" + c.endTime + "'," +c.id + ");";
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