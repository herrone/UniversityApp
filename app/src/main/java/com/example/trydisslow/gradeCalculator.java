package com.example.trydisslow;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class gradeCalculator extends AppCompatActivity {
int target;
int fw;
int fo;
int sw;
int so;
int tw;
int to;
int fow;
int foo;
int fiw;
int fio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_grade_calculator);
        //creating a table (code, targetGrade, ass1 weight, ass1 grade,  ass2 weight, ass2 grade,  ass3 weight, ass3 grade,  ass4 weight, ass4 grade,  ass5 weight, ass5 grade)
       
        TextView targetGrade = (TextView) findViewById(R.id.targetGrade);
        targetGrade.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                targetGrade.setText("");
                return false;
            }
        });
        TextView assignmentWeight1 = (TextView) findViewById(R.id.assignment1weightInput);
        assignmentWeight1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                assignmentWeight1.setText("");
                return false;
            }
        });
        TextView assignmentWeight2 = (TextView) findViewById(R.id.assignment2weightInput);
        assignmentWeight2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               assignmentWeight2.setText("");
                return false;
            }
        });
        TextView assignmentWeight3 = (TextView) findViewById(R.id.assignment3weightInput);
       assignmentWeight3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               assignmentWeight3.setText("");
                return false;
            }
        });
        TextView assignmentWeight4 = (TextView) findViewById(R.id.assignment4weightInput);
        assignmentWeight4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
             assignmentWeight4.setText("");
                return false;
            }
        });
        TextView assignmentWeight5 = (TextView) findViewById(R.id.assignment5weightInput);
        assignmentWeight5.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
              assignmentWeight5.setText("");
                return false;
            }
        });
        TextView obtained1 = (TextView) findViewById(R.id.assignment1obtained);
        obtained1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               obtained1.setText("");
                return false;
            }
        });
        TextView obtained2 = (TextView) findViewById(R.id.assignment2obtained);
        obtained2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                obtained2.setText("");
                return false;
            }
        });
        TextView obtained3 = (TextView) findViewById(R.id.assignment3obtained);
       obtained3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
              obtained3.setText("");
                return false;
            }
        });
        TextView obtained4 = (TextView) findViewById(R.id.assignment4obtained);
        obtained4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               obtained4.setText("");
                return false;
            }
        });
        TextView obtained5 = (TextView) findViewById(R.id.assignment5obtained);
        obtained5.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               obtained5.setText("");
                return false;
            }
        });
        TextView currentAverage = (TextView) findViewById(R.id.currentGradeText);
        currentAverage.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               currentAverage.setText("");
                return false;
            }
        });
        TextView needToObtain = (TextView) findViewById(R.id.needtoobtain);
        needToObtain.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
               needToObtain.setText("");
                return false;
            }
        });
        Spinner moduleCode = (Spinner) findViewById(R.id.moduleCodeListGrades);
        ArrayList<String> moduleCodeArray = new ArrayList<String>();
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
        Cursor query = myBase.rawQuery("SELECT * FROM NEWMODULE3", null);

        if(query.moveToFirst()) {
            while (query.moveToNext()) {
                moduleCodeArray.add(query.getString(1));
            }
        }
        

//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.

        ArrayAdapter<String> moduleCodeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);

        moduleCode.setAdapter(moduleCodeAdapter);
        moduleCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String code = moduleCode.getSelectedItem().toString();
                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

                myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");
                String grade = "SELECT * FROM Grades WHERE code = '" + code + "';";
                Cursor gradeQuery = myBase.rawQuery(grade, null);
                if(gradeQuery.moveToFirst()) {

                    targetGrade.setText(gradeQuery.getInt(1));
                    assignmentWeight1.setText(gradeQuery.getInt(2));
                    assignmentWeight2.setText(gradeQuery.getInt(3));
                    assignmentWeight3.setText(gradeQuery.getInt(4));
                    assignmentWeight4.setText(gradeQuery.getInt(5));
                    assignmentWeight5.setText(gradeQuery.getInt(6));

                    obtained1.setText(gradeQuery.getInt(7));
                    obtained2.setText(gradeQuery.getInt(8));
                    obtained3.setText(gradeQuery.getInt(9));
                    obtained4.setText(gradeQuery.getInt(10));
                    obtained5.setText(gradeQuery.getInt(11));
                    ArrayList<Object> returned =  calculateAverage(gradeQuery.getInt(1), gradeQuery.getInt(2),gradeQuery.getInt(3),gradeQuery.getInt(4),gradeQuery.getInt(5),gradeQuery.getInt(6),gradeQuery.getInt(7),gradeQuery.getInt(8),gradeQuery.getInt(9),gradeQuery.getInt(10),gradeQuery.getInt(11));
                    String currentAverageString = "You have achieved " + (String)returned.get(1) + " possible marks so far";
                    String needToGet = "You need to get an average of " + returned.get(3) + "% in your remaining assignment(s)";
                    currentAverage.setText(currentAverageString);
                    needToObtain.setText(needToGet);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String code =  moduleCode.getSelectedItem().toString();
                String t = (String)targetGrade.getText();
                String fw = (String)assignmentWeight1.getText();
                String sw= (String)assignmentWeight2.getText();
                String tw= (String)assignmentWeight3.getText();
                String fourthw= (String)assignmentWeight4.getText();
                String fifthw= (String)assignmentWeight5.getText();
               int target = Integer.parseInt(t);
               int firstw = Integer.parseInt(fw);
               int secondw = Integer.parseInt(sw);
               int thirdw = Integer.parseInt(tw);
               int fourthweight = Integer.parseInt(fourthw);
               int fifthweight = Integer.parseInt(fifthw);
                String fo = (String)obtained1.getText();
                String so= (String)obtained2.getText();
                String to= (String)obtained3.getText();
                String fourtho= (String)obtained4.getText();
                String fiftho= (String)obtained5.getText();
              //  int target = Integer.parseInt(t);
                int firsto = Integer.parseInt(fo);
                int secondo = Integer.parseInt(so);
                int thirdo = Integer.parseInt(to);
                int fourthobtained = Integer.parseInt(fourtho);
                int fifthobtained = Integer.parseInt(fiftho);
                ArrayList<Object> returned =  calculateAverage(target, firstw, firsto, secondw, secondo, thirdw, thirdo, fourthweight, fourthobtained, fifthweight, fifthobtained);
             String currentAverageString = "You have achieved " + (String)returned.get(1) + " possible marks so far";
             String needToGet = "You need to get an average of " + returned.get(3) + "% in your remaining assignment(s)";
                currentAverage.setText(currentAverageString);
                needToObtain.setText(needToGet);
                
                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

                myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");
                myBase.execSQL("DELETE FROM Grades WHERE code = '" + code + "';");
                myBase.execSQL("INSERT INTO Grades VALUES(" + code + "," + target + "," + firstw + "," + firsto + "," + secondw + "," + secondo + "," + thirdw + "," + thirdo + "," + fourthweight + "," + fourthobtained + "," + fifthweight + "," + fifthobtained + ")");
            
                
            }
        });


    

    }
        //method to calculate average
        public static ArrayList<Object> calculateAverage(int target, int firstWeight, int firstObtained,int secondWeight, int secondObtained, int thirdWeight, int thirdObtained,int fourthWeight, int fourthObtained,int fifthWeight, int fifthObtained){
            ArrayList<Integer> ints = new ArrayList<Integer>();
            int first;
            int second;
            int third;
            int fourth;
            int fifth;
            int pfirst;
            int psecond;
            int pthird;
            int pfourth;
            int pfifth;
            if(firstObtained > 0) {
               first =(firstObtained / 100 * firstWeight);
               pfirst = firstWeight;
            }
            else{
                first = 0;
                pfirst = 0;
            }
            if(secondObtained > 0) {
                second =(secondObtained / 100 * secondWeight);
                psecond = secondWeight;
            }
            else{
               second = 0;
               psecond = 0;
            }
            if(thirdObtained > 0) {
                third =(thirdObtained / 100 * thirdWeight);
                pthird = thirdWeight;
            }
            else{
               third = 0;
               pthird = 0;
            }
            if(fourthObtained > 0) {
                fourth =(fourthObtained / 100 * fourthWeight);
                pfourth = fourthWeight;
            }
            else{
                fourth = 0;
                pfourth = 0;
            }
            if(fifthObtained > 0) {
                fifth=(fifthObtained / 100 * fifthWeight);
                pfifth = fifthWeight;
            }
            else{
                fifth = 0;
                pfifth = 0;
            }

            int marksAchievedthusfar = first+second+third+fourth+fifth;
            int possibleMarksSoFar = pfirst + psecond + pthird + pfourth + pfifth;
            String achieved = marksAchievedthusfar + "/" + possibleMarksSoFar;
            int percentSoFar = (marksAchievedthusfar/possibleMarksSoFar)*100;
            ArrayList<Object> returnable = new ArrayList<>();
            returnable.add(percentSoFar);
            returnable.add(achieved);
            int needToGetMarks = target - marksAchievedthusfar;
            int needToGetPercent = (target - percentSoFar) / (100 - possibleMarksSoFar);
            returnable.add(needToGetMarks);
            returnable.add(needToGetPercent);

            return returnable;


        }




}
