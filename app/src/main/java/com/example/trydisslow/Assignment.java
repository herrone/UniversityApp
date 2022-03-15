package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.Random;

public class Assignment extends AppCompatActivity {
  String dueDate;
   // int percentOfGrade;
    String whichModuleIsTaskFor;
    String notes;
    String title;
    int assignmentCode;

    Random random = new Random();
    public Assignment(){
        assignmentCode = random.nextInt(100);
    }
    public Assignment(String name, String due,  String whichModule, String note){

        dueDate = due;
        //percentOfGrade = percent;
        whichModuleIsTaskFor = whichModule;
        notes = note;
        title = name;
        assignmentCode = random.nextInt(100);
    }
}
