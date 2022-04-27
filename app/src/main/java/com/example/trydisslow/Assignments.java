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

public class Assignments extends AppCompatActivity {
   // this displays the assignments to the screen
    String selected;
    Assignment selectedAssignment;
    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        getSupportActionBar().hide();
        ListView myListView = (ListView) findViewById(R.id.listView);
        ArrayList < Assignment > assignmentList = getAllAssignments(); // all assignments populated

        ArrayList < String > listItems = new ArrayList < String > ();
ArrayList<Module> moduleList = getAllModules();
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
 // for each assignment retrieved , add it to the list in a user friendly print, to then be printed to the screen
        for (Assignment a:
                assignmentList) {
            String name = a.title + " for " + a.whichModuleIsTaskFor;
            listItems.add(name);

        }

        ArrayAdapter < String > adapter = new ArrayAdapter < String > (Assignments.this, android.R.layout.simple_list_item_single_choice, listItems);
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // if an item in the list is chosen
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selected = myListView.getItemAtPosition(i).toString();
                String [] bits =  selected.split(" for "); // split it into the title
                String title = bits[0];
                ArrayList<Assignment> assignments = getAllAssignments();
                for (Assignment a: // for each assignment in the list,
                        assignments) {  // if it's title is the one selected
                    if(a.title.equals(title)){
                        selectedAssignment = a; // make it obvious
                    }
                }
                Toast.makeText(Assignments.this, selected, Toast.LENGTH_LONG).show();
            }
        });



        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        AppCompatButton showPopupBtn = (AppCompatButton) findViewById(R.id.showPopupBtnclass);
        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAssignment == null) { // if thhe user requests more info, but doesn't select an assignment to get the info from
                    Toast.makeText(Assignments.this, "Select an assignment to get more info on", Toast.LENGTH_LONG).show();
//message user
                } else {

                    LayoutInflater layoutInflater = (LayoutInflater) Assignments.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.popupsassignment, null);
                    TextView tv = customView.findViewById(R.id.mytext); // populate a pop up box with the info of the selected assignment

                    tv.setText("Title: " + selectedAssignment.title + "\nModule: " + selectedAssignment.whichModuleIsTaskFor + "\n Due date: " + String.valueOf(selectedAssignment.dueDate) + "\n Notes: " + selectedAssignment.notes + "\n ID: " + selectedAssignment.assignmentId);


                    closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

                    //instantiate popup window
                    popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    //display the popup window
                    popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0); //show pop up

                    //close the popup window on button click
                    closePopupBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        } // banish pop up
                    });

                }
            }
        });
        AppCompatButton addAssignmentButton = (AppCompatButton) findViewById(R.id.buttonGoToAddAssignments);
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (moduleList.size() < 1) { // if the list of modules in system is empty
                    Toast.makeText(Assignments.this, "You cannot add an assignment before adding a module", Toast.LENGTH_LONG).show(); // give message
                } else {
                    startActivity(new Intent(Assignments.this, addAssignment.class)); // else go to add screen
                }
            }

            // startActivity(new Intent(Assignments.this, addAssignment.class));
        });
        AppCompatButton editAssignmentButton = (AppCompatButton) findViewById(R.id.buttonGoToEditAssignments);
        editAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAssignment == null) { // if there's no selected
                    Toast.makeText(Assignments.this, "Please choose an assignment to edit", Toast.LENGTH_LONG).show();// ask for one to be selected
                } else {
                    Intent i = new Intent(Assignments.this, editAssignment.class);
                    i.putExtra("editableAssignment", selectedAssignment.title);// send the info of the selected one to be altered
                    startActivity(i);

                }
            }
        });
        AppCompatButton deleteAssignmentButton = (AppCompatButton) findViewById(R.id.deleteAssignmentButton);
        deleteAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAssignment == null) {// if there's no selected assignemnt to be deleted
                    Toast.makeText(Assignments.this, "Please choose an assignment to delete", Toast.LENGTH_LONG).show();
                    // asks for one
                } else {
                    deleteAssignment(selectedAssignment); // calls deleteAssignment to remove assignment from Db and display

                }
            }
        });
        Button gradeCalc = (Button) findViewById(R.id.gradeCalcbutton); // button to go to grade calculator activity
        gradeCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moduleList.size() < 1) { // if the list of modules in system is empty
                    Toast.makeText(Assignments.this, "You cannot go to Grade Calculator before adding a module", Toast.LENGTH_LONG).show(); // give message
                } else {
                    startActivity(new Intent(Assignments.this, gradeCalculator.class));
                }
            }
        });
 // bottom bar buttons
        AppCompatButton calendarButton = (AppCompatButton) findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, Calendar.class));
            }
        });
        AppCompatButton assignmentButton = (AppCompatButton) findViewById(R.id.buttonAssignments);
        assignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Assignments.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });

        AppCompatButton modulesAndClassesButton = (AppCompatButton) findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, ModulesAndClasses.class));
            }
        });
    }
    public ArrayList < Assignment > getAllAssignments() { // returns a list of all Assignments in the db

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        //Assignment a = new Assignment();
        ArrayList < Assignment > assignmentList = new ArrayList < > ();
        Cursor query = myBase.rawQuery("SELECT * FROM Assignments2", null); // selects everything frkm assignment db
        if (query.moveToFirst()) {
            String name = query.getString(0);
            String code = query.getString(1);
            String stringDueDate = query.getString(2);
            java.text.SimpleDateFormat sdfBackToDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dueDate = null;
            try {
                dueDate = sdfBackToDate.parse(stringDueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String notes = query.getString(3);
            int id = query.getInt(4);
            int hId = query.getInt(5);
            int tfId = query.getInt(6);
            int feId = query.getInt(7);
            Assignment a = new Assignment(name, dueDate, code, notes, id, hId, tfId, feId);
            assignmentList.add(a); // makes an object

            while (query.moveToNext()) { // while there are objects to make
                name = query.getString(0);
                code = query.getString(1);
                stringDueDate = query.getString(2);

                try {
                    dueDate = sdfBackToDate.parse(stringDueDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                notes = query.getString(3);
                id = query.getInt(4);
                hId = query.getInt(5);
                tfId = query.getInt(6);
                feId = query.getInt(7);
                a = new Assignment(name, dueDate, code, notes, id, hId, tfId, feId);
                assignmentList.add(a); // add each to list
            }
        }
        return assignmentList; // return list
    }
    public ArrayList < Module > getAllModules() { // returns a list of every module in the db
        SQLiteDatabase myBase = this.openOrCreateDatabase("Names.db", 0, null);

        ArrayList < Module > moduleList = new ArrayList < > ();
        Module m = new Module();
        Module mo = new Module();
        Cursor moduleQuery = myBase.rawQuery("SELECT * FROM Modules", null); // select all the informatyion from the database
        if (moduleQuery.moveToFirst()) {  // if there's anything there
            m.setNameMod(moduleQuery.getString(0));
            m.setModNotes(moduleQuery.getString(3));
            m.setCourseLeader(moduleQuery.getString(2));
            m.setModuleCode(moduleQuery.getString(1));
            moduleList.add(m);// make a module and add it to the list

            while (moduleQuery.moveToNext()) {
                mo.setNameMod(moduleQuery.getString(0));
                mo.setModNotes(moduleQuery.getString(3));
                mo.setCourseLeader(moduleQuery.getString(2));
                mo.setModuleCode(moduleQuery.getString(1));
                moduleList.add(mo);// continue doing so
            }
        }
        return moduleList; // return list
    }


    public void deleteAssignment(Assignment a) { // takes in an assignment
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

        String deleteStatement = "DELETE FROM Assignments2 WHERE title = '" + a.title + "';"; // deletes it from the db
        myBase.execSQL(deleteStatement);
        startActivity(new Intent(Assignments.this, Assignments.class)); // refreshes page

    }

}