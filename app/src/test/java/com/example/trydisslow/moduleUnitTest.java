package com.example.trydisslow;

import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;

public class moduleUnitTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Test
    public void  createAndTestModuleSettersAndGetters() {

        Module m = new Module();
        m.setModuleCode("SET08");
        m.setCourseLeader("Kevin");
        m.setModNotes("notes");
        m.setNameMod("Mobile Apps");



       assertTrue( m.getModuleCode() == "SET08");
        assertTrue(   m.getCourseLeader() == "Kevin");
        assertTrue(   m.getModNotes() == "notes");
        assertTrue(  m.getNameMod() =="Mobile Apps");

    }

}


