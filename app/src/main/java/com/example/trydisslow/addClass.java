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
import java.util.ArrayList;
import java.util.Random;

public class addClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_class);
        Spinner moduleCodeList = findViewById(R.id.moduleCodeListGrades);
        Spinner dayList = findViewById(R.id.dayList);
        Spinner classTypeList = findViewById(R.id.classTypeList);
        ArrayList < String > moduleCodeArray = new ArrayList < String > ();
        EditText notes = (EditText) findViewById(R.id.addNotesBox);
        TimePicker from = (TimePicker) findViewById(R.id.timePickerFrom);
        TimePicker to = (TimePicker) findViewById(R.id.timePickerTo);
        EditText lecturer = (EditText) findViewById(R.id.lecturerAddBox);

        //if notes are clicked on, the prompt dissapears
         notes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notes.setText("");
                return false;
            }
        });

        //if lecturer is clicked on, the prompt dissapears

        lecturer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lecturer.setText("");
                return false;
            }
        });
        //if location is clicked on, the prompt dissapears

        EditText where = (EditText) findViewById(R.id.addLocationBox);
        where.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                where.setText("");
                return false;
            }
        });

        ArrayList < Module > moduleList = getAllModules(); // fetches all modules to populate spinner
//get all modules
        for (Module m:
                moduleList) {
            moduleCodeArray.add(m.moduleCode);
        } //populate spinner with module codes to be selected from
        ArrayAdapter < String > moduleCodeAdapter = new ArrayAdapter < > (this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);
        moduleCodeList.setAdapter(moduleCodeAdapter);

        String[] classTypeItems = new String[] {
                "choose class type",
                "Lecture",
                "Practical",
                "Tutorial"
        };
        ArrayAdapter < String > classTypeAdapter = new ArrayAdapter < > (this, android.R.layout.simple_spinner_dropdown_item, classTypeItems);
        classTypeList.setAdapter(classTypeAdapter); // populate class type spinner


        String[] days = new String[] {
                "choose day",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        };
        ArrayAdapter < String > adapterDays = new ArrayAdapter < > (this, android.R.layout.simple_spinner_dropdown_item, days);
        dayList.setAdapter(adapterDays); // populate day list

        Random r = new Random();
        AppCompatButton saveClassButton = (AppCompatButton) findViewById(R.id.buttonSaveClass);
        saveClassButton.setOnClickListener(new View.OnClickListener() { // if save button is pressed
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Class c = new Class(); // create and populate a new object
                c.modCode = moduleCodeList.getSelectedItem().toString();
                c.notes = notes.getText().toString();
                c.lecturer = lecturer.getText().toString();
                c.locationOrLink = where.getText().toString();
                c.dayOfClass = dayList.getSelectedItem().toString();
                c.id = r.nextInt(1000);
                int fromHour = from.getHour();
                int fromMinute = from.getMinute();
                if (fromMinute < 10) {
                    c.startTime = fromHour + ":0" + fromMinute;
                } else {
                    c.startTime = fromHour + ":" + fromMinute;
                }
                int toHour = to.getHour();
                int toMinute = to.getMinute();
                if (toMinute < 10) {
                    c.startTime = toHour + ":0" + toMinute;
                } else {
                    c.startTime = toHour + ":" + toMinute;

                }
                c.startTime = fromHour + ":" + fromMinute;
                c.endTime = toHour + ":" + toMinute;
                c.classType = classTypeList.getSelectedItem().toString();

                addClass(c); // add it to the database
                startActivity(new Intent(addClass.this, ModulesAndClasses.class));

            }
        });
    }

    public ArrayList < Module > getAllModules() { // communicates with the database to retrieve an arraylist of modules

        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        ArrayList < Module > moduleList = new ArrayList < > ();
        Cursor moduleQuery = myBase.rawQuery("SELECT * FROM Modules", null);
        if (moduleQuery.moveToFirst()) { // fetches them
            String name = moduleQuery.getString(0);
            String notes = moduleQuery.getString(3);
            String leader = moduleQuery.getString(2);
            String moduleCode = moduleQuery.getString(1);
            Module m = new Module(leader, name, moduleCode, notes);
            moduleList.add(m); // populates objects
            while (moduleQuery.moveToNext()) {
                name = moduleQuery.getString(0);
                notes = moduleQuery.getString(3);
                leader = moduleQuery.getString(2);
                moduleCode = moduleQuery.getString(1);
                m = new Module(leader, name, moduleCode, notes);
                moduleList.add(m); // adds to list
            }
        }
        return moduleList;// returns list
    }

    public void addClass(Class c) { // module accepts a class, then adds it to the database
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        String insertStatement = "INSERT INTO Classes VALUES('" + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "','" + c.locationOrLink + "','" + c.dayOfClass + "','" + c.startTime + "','" + c.endTime + "'," + c.id + ");";
        myBase.execSQL(insertStatement); // inserts into db

    }

}