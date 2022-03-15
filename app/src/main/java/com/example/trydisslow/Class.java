package com.example.trydisslow;

import android.text.format.Time;

import java.time.DayOfWeek;

public class Class {

    String modCode;
    String classType;
    String dayOfClass;
    String startTime;
    String endTime;
    String locationOrLink;
    String notes;
    String lecturer;

    public Class(){

    }
    public Class( String code, String type, String day, String startTimeClass, String endTimeClass, String locationOrLinkClass, String notesClass, String lecturerClass){

        modCode = code;
        classType = type;
        dayOfClass = day;
        startTime = startTimeClass ;
        endTime = endTimeClass ;
        locationOrLink = locationOrLinkClass;
        notes = notesClass;
        lecturer = lecturerClass;

    }
}
