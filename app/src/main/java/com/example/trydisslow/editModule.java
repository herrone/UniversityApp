package com.example.trydisslow;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class editModule extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_module);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        ArrayList < Class > classList = getAllClasses();
        ArrayList < Module > moduleList = getAllModules();
        ArrayList < Assignment > assignmentList = getAllAssignments();
        EditText moduleTitle = (EditText) findViewById(R.id.addModuleTitleBox);
        EditText moduleCod = (EditText) findViewById(R.id.addModuleCodeBox);
        EditText moduleLeader = (EditText) findViewById(R.id.addModuleLeader);
        EditText moduleNotes = (EditText) findViewById(R.id.addModuleNotesBox);

        String editableModule = intent.getExtras().getString("editableModule");

        String title;
        String code;
        String leader;
        String notes;
        String deleteable = "";


        Toast.makeText(this, editableModule,
                Toast.LENGTH_SHORT).show();

        for (Module m: moduleList) {
            if (m.nameMod.equals(editableModule)) {
                moduleNotes.setText(m.modNotes);
                moduleCod.setText(m.moduleCode);
                moduleTitle.setText(m.nameMod);
                moduleLeader.setText(m.courseLeader);

            }

        }
        Module m = new Module();
        AppCompatButton saveModuleButton = (AppCompatButton) findViewById(R.id.buttonSaveModule);
        saveModuleButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                m.nameMod = moduleTitle.getText().toString();

                m.moduleCode = moduleCod.getText().toString();

                m.courseLeader = moduleLeader.getText().toString();

                m.modNotes = moduleNotes.getText().toString();

                updateModule(m, editableModule);

                startActivity(new Intent(editModule.this, ModulesAndClasses.class));

            }
        });

    }
    public ArrayList < Assignment > getAllAssignments() {
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists Assignments(title TEXT, module TEXT, date TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");


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

        myBase.execSQL("CREATE TABLE if not exists Assignments(title TEXT, module TEXT, date TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

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

        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);
        myBase.execSQL("CREATE TABLE if not exists Assignments(title TEXT, module TEXT, date TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

        ArrayList < Class > classList = new ArrayList < > ();
        Class c = new Class();
        Cursor classQuery = myBase.rawQuery("SELECT * FROM NEWCLASSWITHIDS4;", null);
        if (classQuery.moveToFirst()) {
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
    public void addModule(Module m) {
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists Assignments(title TEXT, module TEXT, date TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

        String insertStatement = "INSERT INTO Modules VALUES('" + m.nameMod + "','" + m.moduleCode + "','" + m.courseLeader + "','" + m.modNotes + "')";
        myBase.execSQL(insertStatement);

    }

    public void updateModule(Module m, String deleteable) {
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists Assignments(title TEXT, module TEXT, date TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

        String deleteStatement = "DELETE FROM Modules WHERE code = '" + deleteable + "';";
        myBase.execSQL(deleteStatement);
        String insertStatement = "INSERT INTO Modules VALUES('" + m.nameMod + "','" + m.moduleCode + "','" + m.modNotes + "','" + m.courseLeader + "');";
        myBase.execSQL(insertStatement);
    }


}