package com.example.trydisslow;

public class Module {

    String courseLeader;
    String nameMod;
    String moduleCode;
    Class[] classes;
    String modNotes;
    Assignment[] tasks;


    public String getCourseLeader() {
        return courseLeader;
    }

    public void setCourseLeader(String courseLeader) {
        this.courseLeader = courseLeader;
    }

    public String getNameMod() {
        return nameMod;
    }

    public void setNameMod(String nameMod) {
        this.nameMod = nameMod;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Class[] getClasses() {
        return classes;
    }

    public void setClasses(Class[] classes) {
        this.classes = classes;
    }

    public String getModNotes() {
        return modNotes;
    }

    public void setModNotes(String modNotes) {
        this.modNotes = modNotes;
    }

    public void setTasks(Assignment[] tasks) {
        this.tasks = tasks;
    }

    public Module() {

    }

    public Module(String leader, String name, String code, String notes) {

        courseLeader = leader;
        nameMod = name;
        moduleCode = code;
        modNotes = notes;


    }

}