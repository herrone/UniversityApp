package com.example.trydisslow;

import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class classUnitTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Test
    public void createAndTestClassSettersAndGetters() {

        //  Assignment a = new Assignment();
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

        assertTrue(c.getNotes() == "notes");
        assertTrue(c.getModCode() == "SET08");
        assertTrue( c.getDayOfClass() == "Tuesday");
        assertTrue( c.getClassType() == "Tutorial");
        assertTrue( c.getStartTime() == "13:35");
        assertTrue( c.getEndTime() == "15:35");
        assertTrue(  c.getLocationOrLink() == "online, webex");
        assertTrue(  c.getId() == 45);
        assertTrue(  c.getLecturer() == "Kevin");

    }

}


