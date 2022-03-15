package com.example.trydisslow;


import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DatabaseSingleton extends AppCompatActivity
{
    private static DatabaseSingleton single_instance_db = null;

    SQLiteDatabase myBase;


    public DatabaseSingleton() {

        myBase = this.openOrCreateDatabase("Names.db", 0, null);
       // myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT, colour TEXT);");
    }
        public static DatabaseSingleton getInstance()
        {
            if (single_instance_db == null)
                single_instance_db = new DatabaseSingleton();

            return single_instance_db;
        }



//    public void addAssignment(Assignment a){
//
//        myBase.execSQL("CREATE TABLE if not exists Assignments(title TEXT, module TEXT, date TEXT, notes TEXT);");
//
//        String insertStatement = "INSERT INTO Assignment VALUES('" + a.title + "','" + a.whichModuleIsTaskFor + "',"  + a.dueDate.toString() + ",'" + a.notes +",'" + a.assignmentCode + "')";
//
//        myBase.execSQL(insertStatement);
//
//    }
//    public void addClass( Class a){
//        myBase.execSQL("CREATE TABLE if not exists Classes(title TEXT, module TEXT, date TEXT, notes TEXT);");
//
//        String insertStatement = "INSERT INTO Assignment VALUES('" + a.modCode + "','" + a.classType + "','" + a.locationOrLink + "','"  + a.dayOfClass + "','"  + a.lecturer + "','" + a.notes + "','"+ a.lecturer + "','"+ a.startTime.toString() + "')";
//
//        myBase.execSQL(insertStatement);
//
//
//    }
//    public void addModule(Module a){
//
//
//        String insertStatement = "INSERT INTO Assignment VALUES('" + a.nameMod + "','"+ a.moduleCode + "','"  + a.courseLeader + "','"  + a.modNotes + "')\"";
//
//        this.myBase.execSQL(insertStatement);
//
//    }
//
//    public ArrayList<String> retrieveAllModules() {
//        ArrayList <String> modules = new ArrayList<String>();
//        Cursor query = myBase.rawQuery("SELECT title FROM Modules;", null);
//        if (query.moveToFirst()) {
//            while (!query.isAfterLast()) {
//                int index  = 0;
//                query.getString(index);
//                modules.add(query.getString(index));
//                index++;
//
//            }
//        }
//        return modules;
//    }
//
//
//
//    public ArrayList<String> retrieveAllClasses() {
//        ArrayList <String> classes = new ArrayList<String>();
//        Cursor query = myBase.rawQuery("SELECT title FROM Modules;", null);
//        if (query.moveToFirst()) {
//            while (!query.isAfterLast()) {
//                int index  = 0;
//                query.getString(index);
//                classes.add(query.getString(index));
//                index++;
//
//            }
//        }
//        return classes;
//    }
//    public ArrayList<String> retrieveAllAssignments() {
//        ArrayList <String> assignments = new ArrayList<String>();
//        Cursor query = myBase.rawQuery("SELECT title FROM Assignments;", null);
//        if (query.moveToFirst()) {
//            while (!query.isAfterLast()) {
//                int index  = 0;
//                query.getString(index);
//                assignments.add(query.getString(index));
//                index++;
//            }
//        }
//        return assignments;
//    }
//
//    public Module retrieveModule(){
//        Module m = new Module();
//        return m;
//
//    }
//    public Class retrieveClass(){
//        Class c = new Class();
//        return c;
//    }
//    public Assignment retrieveAssignment(){
//        Assignment a = new Assignment();
//        return a;
//    }







}




