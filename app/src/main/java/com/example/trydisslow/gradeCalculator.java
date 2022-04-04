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
import android.widget.Toast;

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
                 targetGrade.setText(String.valueOf(gradeQuery.getInt(1)));
                   //targetGrade.setText(gradeQuery.getInt(1));
                    assignmentWeight1.setText(String.valueOf(gradeQuery.getInt(2)));
                    assignmentWeight2.setText(String.valueOf(gradeQuery.getInt(4)));
                    assignmentWeight3.setText(String.valueOf(gradeQuery.getInt(6)));
                    assignmentWeight4.setText(String.valueOf(gradeQuery.getInt(8)));
                    assignmentWeight5.setText(String.valueOf(gradeQuery.getInt(10)));

                    obtained1.setText(String.valueOf(gradeQuery.getInt(3)));
                    obtained2.setText(String.valueOf(gradeQuery.getInt(5)));
                    obtained3.setText(String.valueOf(gradeQuery.getInt(7)));
                    obtained4.setText(String.valueOf(gradeQuery.getInt(9)));
                    obtained5.setText(String.valueOf(gradeQuery.getInt(11)));
                    ArrayList<Object> returned =  calculateAverage(gradeQuery.getInt(1), gradeQuery.getInt(2),gradeQuery.getInt(3),gradeQuery.getInt(4),gradeQuery.getInt(5),gradeQuery.getInt(6),gradeQuery.getInt(7),gradeQuery.getInt(8),gradeQuery.getInt(9),gradeQuery.getInt(10),gradeQuery.getInt(11));
                    String currentAverageString = "You have achieved " + (String)returned.get(0) + " possible marks so far";
                    String needToGet = "You need to get an average of " + returned.get(1) + "% in your remaining assignment(s)";
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
                String t = targetGrade.getText().toString();
                String fw = assignmentWeight1.getText().toString();
                String sw= assignmentWeight2.getText().toString();
                String tw= assignmentWeight3.getText().toString();
                String fourthw= assignmentWeight4.getText().toString();
                String fifthw= assignmentWeight5.getText().toString();
               int target = Integer.parseInt(t);
               int firstw = Integer.parseInt(fw);
               int secondw = Integer.parseInt(sw);
               int thirdw = Integer.parseInt(tw);
               int fourthweight = Integer.parseInt(fourthw);
               int fifthweight = Integer.parseInt(fifthw);
                String fo = obtained1.getText().toString();
                String so= obtained2.getText().toString();
                String to= obtained3.getText().toString();
                String fourtho= obtained4.getText().toString();
                String fiftho= obtained5.getText().toString();
              //  int target = Integer.parseInt(t);
                int firsto = Integer.parseInt(fo);
                int secondo = Integer.parseInt(so);
                int thirdo = Integer.parseInt(to);
                int fourthobtained = Integer.parseInt(fourtho);
                int fifthobtained = Integer.parseInt(fiftho);
               if(firstw + secondw + thirdw+ fourthweight + fifthweight != 100){
                   Toast.makeText(gradeCalculator.this, "Please ensure the weighted amounts add up to 100" , Toast.LENGTH_LONG).show();
               }
               else {
                   //
                   ArrayList<Object> returned = calculateAverage(target, firstw, firsto, secondw, secondo, thirdw, thirdo, fourthweight, fourthobtained, fifthweight, fifthobtained);
                   String currentAverageString = "You have achieved " + returned.get(0).toString() + " possible marks so far";
                   String needToGet = "You need to get an average of " + returned.get(1).toString() + "% in your remaining assignment(s)";
                   currentAverage.setText(currentAverageString);
                   needToObtain.setText(needToGet);

                   SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

                   myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");
                   myBase.execSQL("DELETE FROM Grades WHERE code = '" + code + "';");
                   myBase.execSQL("INSERT INTO Grades VALUES('" + code + "'," + target + "," + firstw + "," + firsto + "," + secondw + "," + secondo + "," + thirdw + "," + thirdo + "," + fourthweight + "," + fourthobtained + "," + fifthweight + "," + fifthobtained + ")");

               }
            }
        });


    

    }
        //method to calculate average
        public ArrayList<Object> calculateAverage(int target, int firstWeight, int firstObtained, int secondWeight, int secondObtained, int thirdWeight, int thirdObtained, int fourthWeight, int fourthObtained, int fifthWeight, int fifthObtained){
            ArrayList<Integer> ints = new ArrayList<Integer>();
            int first = 0;
            int second = 0;
            int third=0;
            int fourth=0;
            int fifth=0;
            int pfirst=0;
            int psecond=0;
            int pthird=0;
            int pfourth=0;
            int pfifth=0;
            int possibleMarksSoFar = 0;
            int obtainedMarksSoFar = 0;
            if(firstWeight>0 ) {
                if (firstObtained > 0) {
                    possibleMarksSoFar += firstWeight;
                    obtainedMarksSoFar += (firstObtained * firstWeight)/100;
                }
            }
            else{
                first = 0;
                pfirst = 0;
            }
            if(secondWeight>0 ) {
                if (secondObtained > 0) {
                    possibleMarksSoFar += secondWeight;
                    obtainedMarksSoFar += (secondObtained * secondWeight)/100;
                }
            }
            else{
                second = 0;
                psecond = 0;
            }
            if(thirdWeight>0 ) {
                if (thirdObtained > 0) {
                    possibleMarksSoFar += thirdWeight;
                    obtainedMarksSoFar += (thirdObtained * thirdWeight)/100;
                }
            }
            else{
                third = 0;
                pthird = 0;
            }
            if(fourthWeight>0 ) {
                if (fourthObtained > 0) {
                    possibleMarksSoFar += fourthWeight;
                    obtainedMarksSoFar += (fourthObtained * fourthWeight)/100;
                }
            }
            else{
                fourth = 0;
                pfourth = 0;
            }
            if(fifthWeight>0 ) {
                if (fifthObtained > 0) {
                    possibleMarksSoFar += fifthWeight;
                    obtainedMarksSoFar += (fifthObtained * fifthWeight)/100;
                }
            }
            else{
                fifth = 0;
                pfifth = 0;
            }
           // int marksAchievedthusfar = first+second+third+fourth+fifth;
           // int possibleMarksSoFar = pfirst + psecond + pthird + pfourth + pfifth;
            String achieved = obtainedMarksSoFar + "/" + possibleMarksSoFar;
            int percentSoFar;
            int marks = 0;
            if(possibleMarksSoFar == 0){
                percentSoFar = 0;
            }
//            else{
//                percentSoFar= (obtainedMarksSoFar/possibleMarksSoFar)*100;
//            }

            ArrayList<Object> returnable = new ArrayList<>();
            //returnable.add(percentSoFar);
            returnable.add(achieved);
            int marksRemaining = 100 - possibleMarksSoFar;
            int needToGetMarks = target - obtainedMarksSoFar;
           // Toast.makeText(gradeCalculator.this, "marks to get " + needToGetMarks + "\n percent left " + marksRemaining, Toast.LENGTH_LONG).show();
            int needToGetPercent = 0;
            if (possibleMarksSoFar == 0){
                needToGetPercent = target;
            }
            else {
                needToGetPercent = (needToGetMarks / marksRemaining) * 100;
             //  Toast.makeText(gradeCalculator.this, "marks to get " + needToGetMarks + "\n percent left " + marksRemaining, Toast.LENGTH_LONG).show();
           //     Toast.makeText(gradeCalculator.this, "marks to get " + (needToGetMarks/marksRemaining), Toast.LENGTH_LONG).show();
                 needToGetPercent = (int) (((double) needToGetMarks / (double) marksRemaining) * 100);
                //Toast.makeText(gradeCalculator.this, String.valueOf(marks), Toast.LENGTH_LONG).show();

            }
            //returnable.add(needToGetMarks);
            returnable.add(needToGetPercent);

            return returnable;


        }


}
