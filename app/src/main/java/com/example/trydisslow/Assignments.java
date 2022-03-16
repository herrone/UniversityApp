package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Assignments extends AppCompatActivity {
String selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayList<String> listItems = new ArrayList<String>();

        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        myBase.execSQL("CREATE TABLE if not exists NEWASSIGNMENT2(title TEXT, code TEXT, dueDate TEXT, notes TEXT);");
        Cursor query = myBase.rawQuery("SELECT * FROM NEWASSIGNMENT2", null);


        if(query.moveToFirst()) {
            String name = query.getString(0);
            listItems.add(name);
            while (query.moveToNext()) {
                name = query.getString(0);
                listItems.add(name);
            }
        }
//        listItems.add("second");
//        listItems.add("third");
//        listItems.add("fourth");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Assignments.this,android.R.layout.simple_list_item_single_choice,listItems);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

             selected = myListView.getItemAtPosition(i).toString();

            //    Toast.makeText(Assignments.this, s, Toast.LENGTH_LONG).show();
               // adapter.dismiss(); // If you want to close the adapter
            }
        });



        Button modulesAndClassesButton = (Button)findViewById(R.id.buttonModulesAndClasses);
        modulesAndClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, ModulesAndClasses.class));
            }
        });

        Button settingsButton = (Button)findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, Settings.class));
            }
        });
        Button calendarButton = (Button)findViewById(R.id.buttonCalendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, Calendar.class));
            }
        });
        Button assignmentButton = (Button)findViewById(R.id.buttonAssignments);
        assignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Assignments.this, "Already here", Toast.LENGTH_LONG).show();
            }
        });

        Button addAssignmentButton = (Button)findViewById(R.id.buttonGoToAddAssignments);
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, addAssignment.class));

               // startActivity(new Intent(Assignments.this, addAssignment.class));
            }
        });
        Button editAssignmentButton = (Button)findViewById(R.id.buttonGoToEditAssignments);
        editAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent (Assignments.this, editAssignment.class);

                i.putExtra("editableAssignment", selected);
                startActivity(i);

            }
        });
    }
}