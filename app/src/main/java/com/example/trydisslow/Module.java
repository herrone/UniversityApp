package com.example.trydisslow;

public class Module {

    String courseLeader;
    String nameMod;
    String moduleCode;
    // String colour;
    Class[] classes;
    String modNotes;
    Assignment[] tasks;
    public Module(){

    }

    public Module(String leader, String name, String code, String shade, String notes){

        courseLeader = leader;
        nameMod  = name;
        moduleCode = code;
        // colour = shade;
        modNotes = notes;
        //classes = new Class[6];
        // tasks = new Assignment[6];

    }
//        public void addModule(Module a){
//
//        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT, colour TEXT);");
//
//        String insertStatement = "INSERT INTO Assignment VALUES('" + a.nameMod + "','"+ a.moduleCode + "','"  + a.courseLeader + "','"  + a.modNotes + "')\"";
//
//        myBase.execSQL(insertStatement);
//
//    }
}