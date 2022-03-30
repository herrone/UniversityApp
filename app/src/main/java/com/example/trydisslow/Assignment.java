package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.Random;

public class Assignment extends AppCompatActivity {
  Date dueDate;
   // int percentOfGrade;
    String whichModuleIsTaskFor;
    String notes;
    String title;
    int assignmentId;
    //int id;
    int hourID;
    int tfHourId;
    int feHourId;

    Random random = new Random();
    public Assignment(){
        //assignmentCode = random.nextInt(100);
    }
    public Assignment(String name, Date due,  String whichModule, String note,  int h, int t, int f){

        dueDate = due;
        //percentOfGrade = percent;
        whichModuleIsTaskFor = whichModule;
        notes = note;
        title = name;
        assignmentId = random.nextInt(1000);
        h = hourID;
        t = tfHourId;
        f = feHourId;
    }
    public Assignment(String name, Date due,  String whichModule, String note, int id, int h, int t, int f){

        dueDate = due;
        //percentOfGrade = percent;
        whichModuleIsTaskFor = whichModule;
        notes = note;
        title = name;
        assignmentId = id;
        h = hourID;
        t = tfHourId;
        f = feHourId;
    }
}
