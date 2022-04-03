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

import java.util.ArrayList;

public class ModulesAndClasses extends AppCompatActivity {
String selectedModule;
String selectedClass;
int selectedCode = 0;
    String selected;
    String selectedAssignment;
    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayoutclass;
    LinearLayout linearLayoutModule;
    Class c = new Class();
    Module m = new Module();
    ArrayList<Class> classList = new ArrayList<>();
    ArrayList<Module> moduleList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_and_classes);
        getSupportActionBar().hide();


      ListView myModuleListView = (ListView) findViewById(R.id.moduleListView);
        ArrayList<String> moduleListItems = new ArrayList<String>();
        ListView myClassListView = (ListView) findViewById(R.id.classListView);
        ArrayList<String> classListItems = new ArrayList<String>();

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        myBase.execSQL("CREATE TABLE if not exists NEWMODULE3(title TEXT, code TEXT, leader TEXT, notes TEXT);");

        Cursor moduleQuery = myBase.rawQuery("SELECT * FROM NEWMODULE3", null);
        if(moduleQuery.moveToFirst()){
            m.setNameMod(moduleQuery.getString(0));
            m.setModNotes(moduleQuery.getString(3));
            m.setCourseLeader(moduleQuery.getString(2));
            m.setModuleCode(moduleQuery.getString(1));
            moduleList.add(m);
         //   '" + m.nameMod + "','" + m.moduleCode + "','" + m.courseLeader + "','" + m.modNotes + "'
        while (moduleQuery.moveToNext()){
            m.setNameMod(moduleQuery.getString(0));
            m.setModNotes(moduleQuery.getString(3));
            m.setCourseLeader(moduleQuery.getString(2));
            m.setModuleCode(moduleQuery.getString(1));
            moduleList.add(m);
        }
        }
 Cursor classQuery = myBase.rawQuery("SELECT * FROM NEWCLASSWITHIDS4;", null);
        if(classQuery.moveToFirst()) {
            c.setId(classQuery.getInt(8));
            c.setLecturer(classQuery.getString(2));
            c.setNotes(classQuery.getString(3));
            c.setModCode(classQuery.getString(0));
            c.setEndTime(classQuery.getString(7));
            c.setStartTime(classQuery.getString(6));
            c.setLocationOrLink(classQuery.getString(4));
            c.setClassType(classQuery.getString(1));
            c.setDayOfClass(classQuery.getString(5));
            //    + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "','" + c.locationOrLink+ "','" + c.dayOfClass + "','" +  c.startTime + "','" + c.endTime + "'," +c.id + ");";
            classList.add(c);

            while(classQuery.moveToNext()){
                c.setId(classQuery.getInt(8));
                c.setLecturer(classQuery.getString(2));
                c.setNotes(classQuery.getString(3));
                c.setModCode(classQuery.getString(0));
                c.setEndTime(classQuery.getString(7));
                c.setStartTime(classQuery.getString(6));
                c.setLocationOrLink(classQuery.getString(4));
                c.setClassType(classQuery.getString(1));
                c.setDayOfClass(classQuery.getString(5));
                //    + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "','" + c.locationOrLink+ "','" + c.dayOfClass + "','" +  c.startTime + "','" + c.endTime + "'," +c.id + ");";
                classList.add(c);
            }
        }

        if(moduleQuery.moveToFirst()) {
            String name = moduleQuery.getString(0) + "(" + moduleQuery.getString(1) + ")" ;
           // moduleListItems.add(name);
            while (moduleQuery.moveToNext()) {
                name = moduleQuery.getString(0) + "/" + moduleQuery.getString(1) ;
                moduleListItems.add(name);
            }
        }
        ArrayAdapter<String> moduleAdapter = new ArrayAdapter<String>(ModulesAndClasses.this,R.layout.activity_module_list,moduleListItems);
        myModuleListView.setAdapter(moduleAdapter);
        myModuleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mine =  myModuleListView.getItemAtPosition(i).toString();
               String[] bits = mine.split("/");
                selectedModule = bits[0];

            }
        });

        myModuleListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myClassListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       // myClassListView.getSelectedItem();
//
        myBase.execSQL("CREATE TABLE if not exists NEWCLASSWITHIDS4(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT );");
        // String insertStatement = "INSERT INTO Classes2 VALUES('" + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "');";

        myBase.execSQL("CREATE TABLE if not exists NEWMODULE3(title TEXT, code TEXT, leader TEXT, notes TEXT);");
       // myBase.execSQL("CREATE TABLE if not exists NEWCLASSWITHIDS4(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, start TEXT, finish TEXT, day TEXT, id INT);");
         Cursor classQuery2 = myBase.rawQuery("SELECT NEWCLASSWITHIDS4.type, NEWMODULE3.code, title, day, start, finish, NEWCLASSWITHIDS4.id FROM NEWCLASSWITHIDS4 JOIN NEWMODULE3 ON NEWCLASSWITHIDS4.code = NEWMODULE3.code;", null);

        if(classQuery2.moveToFirst()) {
            selectedCode = classQuery2.getInt(6);
            String name =  classQuery2.getString(2)+ " - " + classQuery2.getString(0)  + " - Every " + classQuery2.getString(3) + "-" + Integer.parseInt(classQuery2.getString(6));
            classListItems.add(name);
//            c.setId(classQuery.getInt(8));
//            c.setLecturer(classQuery.getString(2));
//            c.setNotes(classQuery.getString(3));
//            c.setModCode(classQuery.getString(0));
//            c.setEndTime(classQuery.getString(7));
//            c.setStartTime(classQuery.getString(6));
//            c.setLocationOrLink(classQuery.getString(4));
//            c.setClassType(classQuery.getString(1));
//          c.setDayOfClass(classQuery.getString(5));
//        //    + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "','" + c.locationOrLink+ "','" + c.dayOfClass + "','" +  c.startTime + "','" + c.endTime + "'," +c.id + ");";
//        classList.add(c);
            while (classQuery2.moveToNext()) {
              //  name =  classQuery.getString(2)+ ",(" + classQuery.getString(1)+ ") - " + classQuery.getString(0)  + " Every " + classQuery.getString(3);
             //  name = classQuery.getString(0) + "," + classQuery.getString(2)+ ",(" + classQuery.getString(1)+ "), Every " + classQuery.getString(3)+ ", from " + classQuery.getString(4)+ ", to " + classQuery.getString(5)+ "," + classQuery.getInt(6);
            //    classListItems.add(name);
               // name =  classQuery.getString(2)+ " - " + classQuery.getString(0)  + " - Every " + classQuery.getString(3) + "-" + Integer.parseInt(classQuery.getString(6));
               // classListItems.add(name);
                name =  classQuery2.getString(2)+ " - " + classQuery2.getString(0)  + " - Every " + classQuery2.getString(3) + "-" + Integer.parseInt(classQuery2.getString(6));
                classListItems.add(name);
//                c.setId(classQuery.getInt(8));
//                c.setLecturer(classQuery.getString(2));
//                c.setNotes(classQuery.getString(3));
//                c.setModCode(classQuery.getString(0));
//                c.setEndTime(classQuery.getString(7));
//                c.setStartTime(classQuery.getString(6));
//                c.setLocationOrLink(classQuery.getString(4));
//                c.setClassType(classQuery.getString(1));
//                c.setDayOfClass(classQuery.getString(5));
//                //    + c.modCode + "','" + c.classType + "','" + c.lecturer + "','" + c.notes + "','" + c.locationOrLink+ "','" + c.dayOfClass + "','" +  c.startTime + "','" + c.endTime + "'," +c.id + ");";
//                classList.add(c);
            }
        }
//
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(ModulesAndClasses.this,R.layout.activity_class_list,classListItems);
        myClassListView.setAdapter(classAdapter);
        myClassListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedClass = myClassListView.getItemAtPosition(i).toString();

            }
        });


       AppCompatButton addModuleButton =(AppCompatButton)findViewById(R.id.buttonGoToAddModule);
        addModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, addModule.class));
            }
        });
       AppCompatButton assignmentsButton =(AppCompatButton) findViewById(R.id.buttonAssignments);
        assignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, Assignments.class));
            }
        });
       AppCompatButton modulesAndClassesButton =(AppCompatButton) findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ModulesAndClasses.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });

       AppCompatButton calendarButton =(AppCompatButton) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModulesAndClasses.this, Calendar.class));
            }
        });
       AppCompatButton editModuleButton =(AppCompatButton)findViewById(R.id.editModuleButton);
        editModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedModule == null){
                    Toast.makeText(ModulesAndClasses.this, "Please choose a module to edit", Toast.LENGTH_LONG).show();

                }
                else {
                    Intent i = new Intent(ModulesAndClasses.this, editModule.class);
                    i.putExtra("editableModule", selectedModule);
                    startActivity(i);
                }
            }
        });
       AppCompatButton deleteModuleButton =(AppCompatButton)findViewById(R.id.deleteModuleButton);
        deleteModuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedModule == null){
                    Toast.makeText(ModulesAndClasses.this, "Please choose a module to delete", Toast.LENGTH_LONG).show();

                }
                else {
                    String deleteStatement = "DELETE FROM NEWMODULE3 WHERE title = '" + selectedModule + "';";
                    myBase.execSQL(deleteStatement);
                    startActivity(new Intent(ModulesAndClasses.this, ModulesAndClasses.class));
                }
            }
        });

       AppCompatButton editClassButton =(AppCompatButton)findViewById(R.id.editClassButton);
        editClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedClass == null){
                    Toast.makeText(ModulesAndClasses.this, "Please choose a class to edit", Toast.LENGTH_LONG).show();

                }
                else {

                    Intent i = new Intent(ModulesAndClasses.this, editClass.class);
                    i.putExtra("editableClass", selectedClass);
                    startActivity(i);
                }
            }
        });
       AppCompatButton deleteClassButton =(AppCompatButton)findViewById(R.id.deleteClassButton);
        deleteClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedClass == null) {

                    Toast.makeText(ModulesAndClasses.this, "Please choose a class to delete", Toast.LENGTH_LONG).show();

                } else {
                    String[] bits = selectedClass.split("-");
                    String selectedCode = bits[3];
                   // Toast.makeText(ModulesAndClasses.this, selectedCode, Toast.LENGTH_LONG).show();
                    //String deleteTableStatement = "DROP TABLE NEWCLASSWITHIDS4";
                     String deleteStatement = "DELETE FROM NEWCLASSWITHIDS4 WHERE id = " + selectedCode + ";";
                    myBase.execSQL(deleteStatement);
                   // myBase.execSQL(deleteTableStatement);
                    startActivity(new Intent(ModulesAndClasses.this, ModulesAndClasses.class));
                }
            }
        });
        AppCompatButton addClassButton =(AppCompatButton)findViewById(R.id.buttonGoToAddClass2);
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // myBase.execSQL("CREATE TABLE if not exists NEWMODULE3(title TEXT, code TEXT, leader TEXT, notes TEXT);");

                Cursor moduleQuery = myBase.rawQuery("SELECT * FROM NEWMODULE3", null);

                if(moduleQuery.moveToFirst()) {
                    if (!moduleQuery.moveToNext()) {
                        Toast.makeText(ModulesAndClasses.this, "You cannot add a class before adding a module", Toast.LENGTH_LONG).show();
                    } else {

                        startActivity(new Intent(ModulesAndClasses.this, addClass.class));
                    }
                }
                }


        });

//        LayoutInflater layoutInflater = (LayoutInflater) ModulesAndClasses.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View customView = layoutInflater.inflate(R.layout.popupsclass,null);
//        TextView tv = customView.findViewById(R.id.mytext);
//        linearLayoutclass = (LinearLayout) findViewById(R.id.linearLayoutclass);
        AppCompatButton showPopupBtn = (AppCompatButton) findViewById(R.id.showPopupBtnclass);
        AppCompatButton showPopupBtnMod = (AppCompatButton) findViewById(R.id.showPopupBtnmod);
       // closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
//selectedClass
        linearLayoutclass = (LinearLayout) findViewById(R.id.linearLayoutclass);
        linearLayoutModule = (LinearLayout) findViewById(R.id.linearLayoutmodule);
        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ModulesAndClasses.this, "Already here", Toast.LENGTH_LONG).show();
                LayoutInflater layoutInflater = (LayoutInflater) ModulesAndClasses.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popupsclass,null);
                TextView tv = customView.findViewById(R.id.mytext);
                if(selectedClass == null){
                    tv.setText("Please select a class to get info about");
                }

                else {


                    for (Class c : classList
                    ) {
                        String[] bits = selectedClass.split("-");
                        int selectedCode = Integer.parseInt(bits[3]);
                        if (c.getId() == selectedCode) {
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
////
    //    AppCompatButton showPopupBtnMod = (AppCompatButton) findViewById(R.id.showPopupBtnmod);
        //      AppCompatButton showPopupBtnMod = (AppCompatButton) findViewById(R.id.showPopupBtnmod);
        // closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);


        showPopupBtnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ModulesAndClasses.this, "Already here", Toast.LENGTH_LONG).show();
                LayoutInflater layoutInflater = (LayoutInflater) ModulesAndClasses.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popupsmodules,null);
                TextView tv = customView.findViewById(R.id.mytext);
                if(selectedModule == null){
                    tv.setText("Please select a module to get info about");
                }

                else {
//                String[] bits = selectedModule.split("/");
//                selectedModule = bits[0];
                    for (Module m : moduleList
                    ) {
                        if (m.getNameMod().equals(selectedModule)) {
                            tv.setText("Module name: " + m.getNameMod() + "\n Module code: " + c.getModCode() + "\n Module Leader: " + m.getCourseLeader() + "\n Module Notes: " + m.getModNotes());
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
//
        //instantiate popup window
       // popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        //display the popup window
      //  popupWindow.showAtLocation(linearLayoutclass, Gravity.CENTER, 0, 0);
//
//        //close the popup window on button click
//        closePopupBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });

//        showPopupBtnMod.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv.setText("Hi");
//
//            }
//        });
//
//        closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
//
//        //instantiate popup window
//        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        //display the popup window
//        popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);
//
//        //close the popup window on button click
//        closePopupBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//


    }


    }
