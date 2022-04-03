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
    int id;

    public Class(){

    }

    public String getModCode() {
        return modCode;
    }

    public void setModCode(String modCode) {
        this.modCode = modCode;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getDayOfClass() {
        return dayOfClass;
    }

    public void setDayOfClass(String dayOfClass) {
        this.dayOfClass = dayOfClass;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocationOrLink() {
        return locationOrLink;
    }

    public void setLocationOrLink(String locationOrLink) {
        this.locationOrLink = locationOrLink;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class(String code, String type, String day, String startTimeClass, String endTimeClass, String locationOrLinkClass, String notesClass, String lecturerClass, int id){

        modCode = code;
        classType = type;
        dayOfClass = day;
        startTime = startTimeClass ;
        endTime = endTimeClass ;
        locationOrLink = locationOrLinkClass;
        notes = notesClass;
        lecturer = lecturerClass;
        this.id = id;

    }
}
