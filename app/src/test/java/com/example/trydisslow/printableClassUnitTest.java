package com.example.trydisslow;

import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.Date;


public class printableClassUnitTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Test
    public void createAndTestPrintableClassesSettersAndGetters() {
        PrintableClass pc = new PrintableClass();
        Date d = new Date();
        d.setDate(3);
        d.setYear(2022);
        d.setMonth(4);
        Class c = new Class();
        c.setClassType("Tutorial");
        c.setModCode("SET08");
        c.setDayOfClass("Tuesday");
        c.setStartTime("13:35");
        c.setEndTime("15:35");
        c.setLocationOrLink("online, webex");
        c.setNotes("notes");
        c.setLecturer("Kevin");
        c.setId(45);
        pc.setContained(c);
        pc.setDueDate(d);

        assertTrue(pc.getContained().getNotes() == "notes");
        assertTrue(pc.getContained().getModCode() == "SET08");
        assertTrue( pc.getContained().getDayOfClass() == "Tuesday");
        assertTrue( pc.getContained().getClassType() == "Tutorial");
        assertTrue( pc.getContained().getStartTime() == "13:35");
        assertTrue( pc.getContained().getEndTime() == "15:35");
        assertTrue(  pc.getContained().getLocationOrLink() == "online, webex");
        assertTrue(  pc.getContained().getId() == 45);
        assertTrue(  pc.getContained().getLecturer() == "Kevin");
        assertTrue(pc.getDueDate().getMonth() == 4);
        assertTrue(pc.getDueDate().getDate() == 3);
        assertTrue(pc.getDueDate().getYear() == 2022);

    }

}


