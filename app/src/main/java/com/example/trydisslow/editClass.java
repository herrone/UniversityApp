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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class editClass extends AppCompatActivity {
    String name;
    String code;
    String type;
    String professor;
    String notesText;
    String location;
    String day;
    String start;
    String finish;

    Class inQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        int classCode = intent.getExtras().getInt("editableClass");

        ArrayList < Class > classList = getAllClasses();
        ArrayList < Module > moduleList = getAllModules();
        ArrayList < Assignment > assignmentList = getAllAssignments();


        Toast.makeText(this, String.valueOf(classCode),
                Toast.LENGTH_SHORT).show();
        EditText notes = (EditText) findViewById(R.id.addNotesBox);
        TimePicker from = (TimePicker) findViewById(R.id.timePickerFrom);
        TimePicker to = (TimePicker) findViewById(R.id.timePickerTo);
        EditText lecturer = (EditText) findViewById(R.id.lecturerAddBox);
        EditText where = (EditText) findViewById(R.id.addLocationBox);
        Spinner moduleCodeList = findViewById(R.id.moduleCodeListGrades);
        Spinner dayList = findViewById(R.id.dayList);
        Spinner classTypeList = findViewById(R.id.classTypeList);
        ArrayList < String > moduleCodeArray = new ArrayList < String > ();
        for (Class c:
                classList) {
            if (c.id == classCode) {

                inQuestion = c;

            }

        }

        for (Module m:
                moduleList) {
            moduleCodeArray.add(m.moduleCode);

        }

        ArrayAdapter < String > moduleCodeAdapter = new ArrayAdapter < > (this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);


        notes.setText(inQuestion.notes);
        lecturer.setText(inQuestion.lecturer);
        where.setText(inQuestion.locationOrLink);
        String[] classTypeItems = new String[] {
                "choose class type",
                "Lecture",
                "Practical",
                "Tutorial"
        };
        ArrayAdapter < String > classTypeAdapter = new ArrayAdapter < > (this, android.R.layout.simple_spinner_dropdown_item, classTypeItems);

        classTypeList.setAdapter(classTypeAdapter);
        moduleCodeList.setAdapter(moduleCodeAdapter);

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
        dayList.setAdapter(adapterDays);
        AppCompatButton saveClassButton = (AppCompatButton) findViewById(R.id.buttonSaveClass);
        saveClassButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                inQuestion.modCode = moduleCodeList.getSelectedItem().toString();
                inQuestion.notes = notes.getText().toString();
                inQuestion.lecturer = lecturer.getText().toString();
                inQuestion.locationOrLink = where.getText().toString();
                inQuestion.dayOfClass = dayList.getSelectedItem().toString();


                inQuestion.startTime = from.toString();
                inQuestion.endTime = to.toString();
                inQuestion.classType = classTypeList.getSelectedItem().toString();
                updateClass(inQuestion);

                startActivity(new Intent(editClass.this, ModulesAndClasses.class));

            }
        });



    }
    public ArrayList < Assignment > getAllAssignments() {
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        ArrayList < Assignment > assignmentList = new ArrayList < > ();
        Cursor query = myBase.rawQuery("SELECT * FROM NEWASSIGNMENTSWITHIDS2", null);
        if (query.moveToFirst()) {
            String name = query.getString(0);
            String code = query.getString(1);
            String stringDueDate = query.getString(2);
            java.text.SimpleDateFormat sdfBackToDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dueDate = null;
            try {
                dueDate = sdfBackToDate.parse(stringDueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String notes = query.getString(3);
            int id = query.getInt(4);
            int hId = query.getInt(5);
            int tfId = query.getInt(6);
            int feId = query.getInt(7);
            Assignment a = new Assignment(name, dueDate, code, notes, id, hId, tfId, feId);
            assignmentList.add(a);

            while (query.moveToNext()) {
                name = query.getString(0);
                code = query.getString(1);
                stringDueDate = query.getString(2);

                try {
                    dueDate = sdfBackToDate.parse(stringDueDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                notes = query.getString(3);
                id = query.getInt(4);
                hId = query.getInt(5);
                tfId = query.getInt(6);
                feId = query.getInt(7);
                a = new Assignment(name, dueDate, code, notes, id, hId, tfId, feId);
                assignmentList.add(a);
            }
        }

        return assignmentList;
    }
    public ArrayList < Module > getAllModules() {
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        ArrayList < Module > moduleList = new ArrayList < > ();
        Module m = new Module();
        Cursor moduleQuery = myBase.rawQuery("SELECT * FROM Modules", null);
        if (moduleQuery.moveToFirst()) {
            m.setNameMod(moduleQuery.getString(0));
            m.setModNotes(moduleQuery.getString(3));
            m.setCourseLeader(moduleQuery.getString(2));
            m.setModuleCode(moduleQuery.getString(1));
            moduleList.add(m);
            while (moduleQuery.moveToNext()) {
                Module mo = new Module();
                mo.setNameMod(moduleQuery.getString(0));
                mo.setModNotes(moduleQuery.getString(3));
                mo.setCourseLeader(moduleQuery.getString(2));
                mo.setModuleCode(moduleQuery.getString(1));
                moduleList.add(mo);
            }
        }
        return moduleList;
    }
    public ArrayList < Class > getAllClasses() {

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        ArrayList < Class > classList = new ArrayList < > ();
        Class c = new Class();
        Class co = new Class();
        Cursor classQuery = myBase.rawQuery("SELECT * FROM Classes;", null);
        if (classQuery.moveToFirst()) {
            co.setId(classQuery.getInt(8));
            co.setLecturer(classQuery.getString(2));
            co.setNotes(classQuery.getString(3));
            co.setModCode(classQuery.getString(0));
            co.setEndTime(classQuery.getString(7));
            co.setStartTime(classQuery.getString(6));
            co.setLocationOrLink(classQuery.getString(4));
            co.setClassType(classQuery.getString(1));
            co.setDayOfClass(classQuery.getString(5));
            classList.add(co);

            while (classQuery.moveToNext()) {
                c.setId(classQuery.getInt(8));
                c.setLecturer(classQuery.getString(2));
                c.setNotes(classQuery.getString(3));
                c.setModCode(classQuery.getString(0));
                c.setEndTime(classQuery.getString(7));
                c.setStartTime(classQuery.getString(6));
                c.setLocationOrLink(classQuery.getString(4));
                c.setClassType(classQuery.getString(1));
                c.setDayOfClass(classQuery.getString(5));
                classList.add(c);
            }
        }
        return classList;
    }
    public void updateClass(Class c) {
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        String deleteStatement = "DELETE FROM Classes WHERE id = " + c.id + ";";
        myBase.execSQL(deleteStatement);
        String insertStatement = "INSERT INTO Classes VALUES('" + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "','" + c.locationOrLink + "','" + c.dayOfClass + "','" + c.startTime + "','" + c.endTime + "'," + c.id + ");";
        myBase.execSQL(insertStatement);
    }

}