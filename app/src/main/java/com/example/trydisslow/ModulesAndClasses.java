package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ModulesAndClasses extends AppCompatActivity {

    String selectedModule;
    String selectedClass;
    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayoutclass;
    LinearLayout linearLayoutModule;
    Module moduleSelected = null;
    Class classSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modules_and_classes);
        getSupportActionBar().hide();

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);


        ListView myModuleListView = (ListView) findViewById(R.id.moduleListView);
        myModuleListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayList < String > moduleListItems = new ArrayList < String > ();
        ListView myClassListView = (ListView) findViewById(R.id.classListView);
        myClassListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayList < String > classListItems = new ArrayList < String > ();
        AppCompatButton showPopupBtn = (AppCompatButton) findViewById(R.id.showPopupBtnclass);
        AppCompatButton showPopupBtnMod = (AppCompatButton) findViewById(R.id.showPopupBtnmod);

        linearLayoutclass = (LinearLayout) findViewById(R.id.linearLayoutclass);
        linearLayoutModule = (LinearLayout) findViewById(R.id.linearLayoutmodule);
        ArrayList < Class > classList = getAllClasses();
        ArrayList < Module > moduleList = getAllModules();


        for (Module m: moduleList) {
            String name = " " + m.nameMod + "/" + m.moduleCode;
            moduleListItems.add(name);

        }
        for (Class c: classList) {
            String name = c.modCode + " - " + c.classType + " - Every " + c.dayOfClass + "-" + c.id;
            classListItems.add(name);

        }
        ArrayAdapter < String > moduleAdapter = new ArrayAdapter < String > (ModulesAndClasses.this, android.R.layout.simple_list_item_single_choice, moduleListItems);
        myModuleListView.setAdapter(moduleAdapter);
        myModuleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView < ? > adapterView, View view, int i, long l) {
                String mine = myModuleListView.getItemAtPosition(i).toString();
                String[] bits = mine.split("/");
                selectedModule = bits[1];
                ArrayList < Module > modulez = getAllModules();
                for (Module m: modulez) {
                    if (m.moduleCode.equals(bits[1])) {
                        moduleSelected = m;
                    }

                }


            }
        });
        ArrayAdapter < String > classAdapter = new ArrayAdapter < String > (ModulesAndClasses.this, android.R.layout.simple_list_item_single_choice, classListItems);
        myClassListView.setAdapter(classAdapter);
        myClassListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView < ? > adapterView, View view, int i, long l) {
                selectedClass = myClassListView.getItemAtPosition(i).toString();
                String[] bits = selectedClass.split("-");
                selectedClass = bits[3];
                String[] bits2 = selectedClass.split(" ");
                selectedClass = bits2[0];
                ArrayList < Class > classes = getAllClasses();
                for (Class c: classes) {
                    if (c.id == Integer.parseInt(selectedClass)) {
                        classSelected = c;
                    }

                }

            }
        });

        myModuleListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myClassListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        showPopupBtnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ModulesAndClasses.this, "Already here", Toast.LENGTH_LONG).show();
                LayoutInflater layoutInflater = (LayoutInflater) ModulesAndClasses.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popupsmodules, null);
                TextView tv = customView.findViewById(R.id.mytext);
                if (selectedModule == null) {
                    tv.setText("Please select a module to get selecte about");
                } else {
                    ArrayList < Module > modulez = getAllModules();

                    for (Module m: modulez) {
                        if (m.moduleCode.equals(selectedModule)) {
                            moduleSelected = m;

                            tv.setText("Module name: " + m.getNameMod() + "\n Module code: " + m.moduleCode + "\n Module Leader: " + m.getCourseLeader() + "\n Module Notes: " + m.getModNotes());
                        }

                    }
                }

                closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

                //instantiate popup windowr
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayoutModule, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });
        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ModulesAndClasses.this, "Already here", Toast.LENGTH_LONG).show();
                LayoutInflater layoutInflater = (LayoutInflater) ModulesAndClasses.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popupsclass, null);
                TextView tv = customView.findViewById(R.id.mytext);
                ArrayList < Class > classes = getAllClasses();
                if (selectedClass == null) {
                    tv.setText("Please select a class to get info about");
                } else {

                    for (Class c: classes) {

                        if (c.id == classSelected.id) {
                            tv.setText("Class module: " + c.getModCode() + "\n Class Type: " + c.getClassType() + "\n Class Day: " + c.getDayOfClass() + "\n Start time: " + c.getStartTime() + "\n End time: " + c.getEndTime() + "\n Lecturer: " + c.getLecturer() + "\n Location: " + c.getLocationOrLink() + "\n Notes: " + c.getNotes());
                        }
                    }
                }

                closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayoutclass, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });

        AppCompatButton addModuleButton = (AppCompatButton) findViewById(R.id.buttonGoToAddModule);
        addModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, addModule.class));
            }
        });

        AppCompatButton editModuleButton = (AppCompatButton) findViewById(R.id.editModuleButton);
        editModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moduleSelected == null) {
                    Toast.makeText(ModulesAndClasses.this, "Please choose a module to edit", Toast.LENGTH_LONG).show();

                } else {
                    Intent i = new Intent(ModulesAndClasses.this, editModule.class);
                    i.putExtra("editableModule", moduleSelected.nameMod);

                    startActivity(i);
                }
            }
        });
        AppCompatButton deleteModuleButton = (AppCompatButton) findViewById(R.id.deleteModuleButton);
        deleteModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedModule == null) {
                    Toast.makeText(ModulesAndClasses.this, "Please choose a module to delete", Toast.LENGTH_LONG).show();

                } else {

                    deleteModule(moduleSelected);
                    startActivity(new Intent(ModulesAndClasses.this, ModulesAndClasses.class));
                }
            }
        });

        AppCompatButton editClassButton = (AppCompatButton) findViewById(R.id.editClassButton);
        editClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classSelected == null) {
                    Toast.makeText(ModulesAndClasses.this, "Please choose a class to edit", Toast.LENGTH_LONG).show();

                } else {

                    Intent i = new Intent(ModulesAndClasses.this, editClass.class);
                    i.putExtra("editableClass", classSelected.id);
                    Toast.makeText(ModulesAndClasses.this, String.valueOf(classSelected.id), Toast.LENGTH_LONG).show();
                    startActivity(i);
                }
            }
        });
        AppCompatButton deleteClassButton = (AppCompatButton) findViewById(R.id.deleteClassButton);
        deleteClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classSelected == null) {
                    Toast.makeText(ModulesAndClasses.this, "Please choose a class to delete", Toast.LENGTH_LONG).show();

                } else {
                    deleteClass(classSelected);

                    startActivity(new Intent(ModulesAndClasses.this, ModulesAndClasses.class));
                }
            }
        });
        AppCompatButton addClassButton = (AppCompatButton) findViewById(R.id.buttonGoToAddClass2);
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList < Module > moduleList2 = getAllModules();
                if (moduleList2.isEmpty()) {
                    Toast.makeText(ModulesAndClasses.this, "You cannot add a class before adding a module", Toast.LENGTH_LONG).show();
                } else {

                    startActivity(new Intent(ModulesAndClasses.this, addClass.class));
                }

            }


        });

        AppCompatButton assignmentsButton = (AppCompatButton) findViewById(R.id.buttonAssignments);
        assignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, Assignments.class));
            }
        });
        AppCompatButton modulesAndClassesButton = (AppCompatButton) findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ModulesAndClasses.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });

        AppCompatButton calendarButton = (AppCompatButton) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, Calendar.class));
            }
        });
    }



    public ArrayList < Module > getAllModules() {
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);


        ArrayList < Module > moduleList = new ArrayList < > ();
        Module m = new Module();
        Cursor moduleQuery = myBase.rawQuery("SELECT * FROM Modules", null);
        if (moduleQuery.moveToFirst()) {
            m.setNameMod(moduleQuery.getString(0));
            m.setModNotes(moduleQuery.getString(3));
            m.setCourseLeader(moduleQuery.getString(2));
            m.setModuleCode(moduleQuery.getString(1));
            moduleList.add(m);
            while (moduleQuery.moveToNext()) {
                Module mo = new Module();
                mo.setNameMod(moduleQuery.getString(0));
                mo.setModNotes(moduleQuery.getString(3));
                mo.setCourseLeader(moduleQuery.getString(2));
                mo.setModuleCode(moduleQuery.getString(1));
                moduleList.add(mo);
            }
        }
        return moduleList;
    }
    public ArrayList < Class > getAllClasses() {

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        ArrayList < Class > classList = new ArrayList < > ();
        Class c = new Class();
        Class co = new Class();
        Cursor classQuery = myBase.rawQuery("SELECT * FROM Classes;", null);
        if (classQuery.moveToFirst()) {
            co.setId(classQuery.getInt(8));
            co.setLecturer(classQuery.getString(2));
            co.setNotes(classQuery.getString(3));
            co.setModCode(classQuery.getString(0));
            co.setEndTime(classQuery.getString(7));
            co.setStartTime(classQuery.getString(6));
            co.setLocationOrLink(classQuery.getString(4));
            co.setClassType(classQuery.getString(1));
            co.setDayOfClass(classQuery.getString(5));
            classList.add(co);

            while (classQuery.moveToNext()) {
                c.setId(classQuery.getInt(8));
                c.setLecturer(classQuery.getString(2));
                c.setNotes(classQuery.getString(3));
                c.setModCode(classQuery.getString(0));
                c.setEndTime(classQuery.getString(7));
                c.setStartTime(classQuery.getString(6));
                c.setLocationOrLink(classQuery.getString(4));
                c.setClassType(classQuery.getString(1));
                c.setDayOfClass(classQuery.getString(5));
                classList.add(c);
            }
        }
        return classList;
    }
    public void updateClass(Class c) {
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);


        String deleteStatement = "DELETE FROM Classes WHERE code = '" + c.modCode + "' AND type = '" + c.classType + "'";
        myBase.execSQL(deleteStatement);
        String insertStatement = "INSERT INTO Classes VALUES('" + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "','" + c.locationOrLink + "','" + c.dayOfClass + "','" + c.startTime + "','" + c.endTime + "'," + c.id + ");";
        myBase.execSQL(insertStatement);
    }
    public void deleteClass(Class c) {
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);


        String deleteStatement = "DELETE FROM Classes WHERE id = " + c.id + ";";
        myBase.execSQL(deleteStatement);
    }

    public void deleteModule(Module m) {
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);
        String deleteStatement = "DELETE FROM Modules WHERE code = '" + m.moduleCode + "';";
        myBase.execSQL(deleteStatement);
    }

}