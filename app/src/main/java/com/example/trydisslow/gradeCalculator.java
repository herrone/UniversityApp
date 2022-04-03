package com.example.trydisslow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class gradeCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_grade_calculator);
        //creating a table (code, targetGrade, ass1 weight, ass1 grade,  ass2 weight, ass2 grade,  ass3 weight, ass3 grade,  ass4 weight, ass4 grade,  ass5 weight, ass5 grade)
        TextView targetGrade = (TextView) findViewById(R.id.targetGrade);
        TextView assignmentWeight1 = (TextView) findViewById(R.id.assignment1weightInput);
        TextView assignmentWeight2 = (TextView) findViewById(R.id.assignment2weightInput);
        TextView assignmentWeight3 = (TextView) findViewById(R.id.assignment3weightInput);
        TextView assignmentWeight4 = (TextView) findViewById(R.id.assignment4weightInput);
        TextView assignmentWeight5 = (TextView) findViewById(R.id.assignment5weightInput);
        TextView obtained1 = (TextView) findViewById(R.id.assignment1obtained);
        TextView obtained2 = (TextView) findViewById(R.id.assignment2obtained);
        TextView obtained3 = (TextView) findViewById(R.id.assignment3obtained);
        TextView obtained4 = (TextView) findViewById(R.id.assignment4obtained);
        TextView obtained5 = (TextView) findViewById(R.id.assignment5obtained);
        TextView currentAverage = (TextView) findViewById(R.id.currentGradeText);
        TextView needToObtain = (TextView) findViewById(R.id.needtoobtain);
        Spinner moduleCode = (Spinner) findViewById(R.id.moduleCodeListGrades);
        moduleCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // Cursor gradeQuery =;

                //selecting * from table, then populate each box
              //  assignmentWeight1.setText(gradeQuery.getInteger());
                //call method to calculate average so far
                //}
            }
        });

        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
