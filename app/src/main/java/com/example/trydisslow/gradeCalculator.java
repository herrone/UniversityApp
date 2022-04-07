package com.example.trydisslow;

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
        ArrayList < String > moduleCodeArray = new ArrayList < String > ();
        ArrayList < Module > modules = getAllModules();
        for (Module m:
                modules) {
            moduleCodeArray.add(m.moduleCode);

        }


        ArrayAdapter < String > moduleCodeAdapter = new ArrayAdapter < > (this, android.R.layout.simple_spinner_dropdown_item, moduleCodeArray);

        moduleCode.setAdapter(moduleCodeAdapter);
        moduleCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView < ? > adapterView, View view, int i, long l) {
                String code = moduleCode.getSelectedItem().toString();


                SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
                myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");
                String grade = "SELECT * FROM Grades WHERE code = '" + code + "';";
                Cursor gradeQuery = myBase.rawQuery(grade, null);
                if (gradeQuery.moveToFirst()) {
                    targetGrade.setText(gradeQuery.getInt(1));
                    assignmentWeight1.setText(gradeQuery.getInt(2));
                    assignmentWeight2.setText(gradeQuery.getInt(4));
                    assignmentWeight3.setText(gradeQuery.getInt(6));
                    assignmentWeight4.setText(gradeQuery.getInt(8));
                    assignmentWeight5.setText(gradeQuery.getInt(10));

                    obtained1.setText(gradeQuery.getInt(3));
                    obtained2.setText(gradeQuery.getInt(5));
                    obtained3.setText(gradeQuery.getInt(7));
                    obtained4.setText(gradeQuery.getInt(9));
                    obtained5.setText(gradeQuery.getInt(11));
                    myBase.execSQL("DELETE FROM Grades WHERE code = '" + gradeQuery.getString(0) + "';");
                    myBase.execSQL("INSERT INTO  Grades VALUES('" + code + "'," + gradeQuery.getInt(1) + "," + gradeQuery.getInt(2) + "," + gradeQuery.getInt(3) + "," + gradeQuery.getInt(4) + "," + gradeQuery.getInt(5) + "," + gradeQuery.getInt(6) + "," + gradeQuery.getInt(7) + "," + gradeQuery.getInt(8) + "," + gradeQuery.getInt(9) + "," + gradeQuery.getInt(10) + "," + gradeQuery.getInt(11) + ");");

                    ArrayList < Object > returned = calculateAverage(gradeQuery.getInt(1), gradeQuery.getInt(2), gradeQuery.getInt(3), gradeQuery.getInt(4), gradeQuery.getInt(5), gradeQuery.getInt(6), gradeQuery.getInt(7), gradeQuery.getInt(8), gradeQuery.getInt(9), gradeQuery.getInt(10), gradeQuery.getInt(11));
                    String currentAverageString = "You have achieved " + (String) returned.get(0) + " possible marks so far";
                    String needToGet = "You need to get an average of " + returned.get(1) + "% in your remaining assignment(s)";
                    currentAverage.setText(currentAverageString);
                    needToObtain.setText(needToGet);
                }

            }

            @Override
            public void onNothingSelected(AdapterView < ? > adapterView) {

            }

        });

        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grade g = new Grade();
                String code = moduleCode.getSelectedItem().toString();
                String t = targetGrade.getText().toString();
                String fw = assignmentWeight1.getText().toString();
                String sw = assignmentWeight2.getText().toString();
                String tw = assignmentWeight3.getText().toString();
                String fourthw = assignmentWeight4.getText().toString();
                String fifthw = assignmentWeight5.getText().toString();
                int target = Integer.parseInt(t);
                int firstw = Integer.parseInt(fw);
                int secondw = Integer.parseInt(sw);
                int thirdw = Integer.parseInt(tw);
                int fourthweight = Integer.parseInt(fourthw);
                int fifthweight = Integer.parseInt(fifthw);
                String fo = obtained1.getText().toString();
                String so = obtained2.getText().toString();
                String to = obtained3.getText().toString();
                String fourtho = obtained4.getText().toString();
                String fiftho = obtained5.getText().toString();

                int firsto = Integer.parseInt(fo);
                int secondo = Integer.parseInt(so);
                int thirdo = Integer.parseInt(to);
                int fourthobtained = Integer.parseInt(fourtho);
                int fifthobtained = Integer.parseInt(fiftho);
                g.code = code;
                g.target = target;
                g.firstWeight = firstw;
                g.firstObtained = firsto;
                g.secondObtained = secondo;
                if (firstw + secondw + thirdw + fourthweight + fifthweight != 100) {
                    Toast.makeText(gradeCalculator.this, "Please ensure the weighted amounts add up to 100", Toast.LENGTH_LONG).show();
                } else {

                    ArrayList < Object > returned = calculateAverage(target, firstw, firsto, secondw, secondo, thirdw, thirdo, fourthweight, fourthobtained, fifthweight, fifthobtained);
                    String currentAverageString = "You have achieved " + returned.get(0).toString() + " possible marks so far";
                    String needToGet = "You need to get an average of " + returned.get(1).toString() + "% in your remaining assignment(s)";
                    currentAverage.setText(currentAverageString);
                    needToObtain.setText(needToGet);


                }
            }
        });




    }

    public ArrayList < Object > calculateAverage(int target, int firstWeight, int firstObtained, int secondWeight, int secondObtained, int thirdWeight, int thirdObtained, int fourthWeight, int fourthObtained, int fifthWeight, int fifthObtained) {
        ArrayList<Integer> ints = new ArrayList<Integer>();
        int first = 0;
        int second = 0;
        int third = 0;
        int fourth = 0;
        int fifth = 0;
        int pfirst = 0;
        int psecond = 0;
        int pthird = 0;
        int pfourth = 0;
        int pfifth = 0;
        int possibleMarksSoFar = 0;
        int obtainedMarksSoFar = 0;
        if (firstWeight > 0) {
            if (firstObtained > 0) {
                possibleMarksSoFar += firstWeight;
                obtainedMarksSoFar += (firstObtained * firstWeight) / 100;
            }
        } else {
            first = 0;
            pfirst = 0;
        }
        if (secondWeight > 0) {
            if (secondObtained > 0) {
                possibleMarksSoFar += secondWeight;
                obtainedMarksSoFar += (secondObtained * secondWeight) / 100;
            }
        } else {
            second = 0;
            psecond = 0;
        }
        if (thirdWeight > 0) {
            if (thirdObtained > 0) {
                possibleMarksSoFar += thirdWeight;
                obtainedMarksSoFar += (thirdObtained * thirdWeight) / 100;
            }
        } else {
            third = 0;
            pthird = 0;
        }
        if (fourthWeight > 0) {
            if (fourthObtained > 0) {
                possibleMarksSoFar += fourthWeight;
                obtainedMarksSoFar += (fourthObtained * fourthWeight) / 100;
            }
        } else {
            fourth = 0;
            pfourth = 0;
        }
        if (fifthWeight > 0) {
            if (fifthObtained > 0) {
                possibleMarksSoFar += fifthWeight;
                obtainedMarksSoFar += (fifthObtained * fifthWeight) / 100;
            }
        } else {
            fifth = 0;
            pfifth = 0;
        }
        String achieved = obtainedMarksSoFar + "/" + possibleMarksSoFar;
        int percentSoFar;
        int marks = 0;
        if (possibleMarksSoFar == 0) {
            percentSoFar = 0;
        }


        ArrayList<Object> returnable = new ArrayList<>();

        returnable.add(achieved);
        int marksRemaining = 100 - possibleMarksSoFar;
        int needToGetMarks = target - obtainedMarksSoFar;
        int needToGetPercent = 0;
        if (possibleMarksSoFar == 0) {
            needToGetPercent = target;
        } else {
            needToGetPercent = (needToGetMarks / marksRemaining) * 100;
            needToGetPercent = (int) (((double) needToGetMarks / (double) marksRemaining) * 100);

        }

        returnable.add(needToGetPercent);

        return returnable;

    }
        public ArrayList < Module > getAllModules() {
            SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);

            myBase.execSQL("CREATE TABLE if not exists Assignments2(title TEXT, module TEXT, date TEXT, notes TEXT);");
            myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
            myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
            myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");

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

}