package com.example.trydisslow;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import java.util.Date;


public class assignmentUnitTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Test
    public void createAndTestAssignmentSettersAndGetters() {

    //    String name, Date due,  String whichModule, String note,  int h, int t, int f
      // Assignment a = new Assignment();
        Date date = new Date();
        date.setDate(2);
        date.setYear(2025);
        date.setMonth(9);
      Assignment b = new Assignment("myname",date, "EMILYMOD", "nooootes", 0, 0, 0 );
        assertTrue(b.getNotes() == "nooootes");
        assertTrue(b.getWhichModuleIsTaskFor() == "EMILYMOD");
      assertTrue(b.dueDate.getMonth() == 9);
        assertTrue(b.dueDate.getDate() == 2);
        assertTrue(b.dueDate.getYear() == 2025);
        assertTrue(b.getName() == "myname");
        assertTrue(b.getHourID() == 0);
        assertTrue(b.getTfHourId() == 0);
        assertTrue(b.getFeHourId() == 0);
       Date d = new Date();
       d.setDate(3);
       d.setYear(2022);
       d.setMonth(4);
       b.setTitle("my assignment");
       b.setAssignmentId(5);
       b.setDueDate(d);
       b.setNotes("notes");
       b.setAssignmentId(45);
       b.setWhichModuleIsTaskFor("SET08");
       int id = b.assignmentId;

        assertTrue(b.notes == "notes");
        assertTrue(b.whichModuleIsTaskFor == "SET08");
        assertTrue( id < 1000);
        assertTrue(b.dueDate.getMonth() == 4);
        assertTrue(b.dueDate.getDate() == 3);
        assertTrue(b.dueDate.getYear() == 2022);
        assertTrue(b.title == "my assignment");
//assertTrue(4 == 4);
    }

}


