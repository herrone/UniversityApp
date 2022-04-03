package com.example.trydisslow;

import android.text.format.Time;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

public class PrintableClass {

  Class contained;
  Date dueDate;

    public PrintableClass(){

    }

    public Class getContained() {
        return contained;
    }

    public void setContained(Class contained) {
        this.contained = contained;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public PrintableClass(Class a, Date d){

        contained = a;
        dueDate = d;


    }
//    public ArrayList<Class> makeArraylistClasses(Class a){
//        ArrayList<Class> classes = new ArrayList<>();
//
//
//
//    return classes;
//    }

}







