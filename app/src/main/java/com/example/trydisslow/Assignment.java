package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;
import java.util.Random;

public class Assignment extends AppCompatActivity {

    Date dueDate;
    String whichModuleIsTaskFor;
    String notes;
    String title;
    int assignmentId;
    //int id;
    int hourID; // these are to track notifications
    int tfHourId;// these are to track notifications
    int feHourId;// these are to track notifications
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getWhichModuleIsTaskFor() {
        return whichModuleIsTaskFor;
    }

    public void setWhichModuleIsTaskFor(String whichModuleIsTaskFor) {
        this.whichModuleIsTaskFor = whichModuleIsTaskFor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getName() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getHourID() {
        return hourID;
    }

    public void setHourID(int hourID) {
        this.hourID = hourID;
    }

    public int getTfHourId() {
        return tfHourId;
    }

    public void setTfHourId(int tfHourId) {
        this.tfHourId = tfHourId;
    }

    public int getFeHourId() {
        return feHourId;
    }

    public void setFeHourId(int feHourId) {
        this.feHourId = feHourId;
    }

    Random random = new Random();

    public Assignment() { // basic constructor

    }
    public Assignment(String name, Date due, String whichModule, String note, int h, int t, int f) {
 // populated constructor
        dueDate = due;
        whichModuleIsTaskFor = whichModule;
        notes = note;
        title = name;
        assignmentId = random.nextInt(1000);
        h = hourID;
        t = tfHourId;
        f = feHourId;
    }
    public Assignment(String name, Date due, String whichModule, String note, int id, int h, int t, int f) {
        // populated constructor
        dueDate = due;
        whichModuleIsTaskFor = whichModule;
        notes = note;
        title = name;
        assignmentId = id;
        h = hourID;
        t = tfHourId;
        f = feHourId;
    }

}