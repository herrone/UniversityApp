package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
         //used for testing
//        myBase.execSQL("DROP TABLE Assignments2;");
//        myBase.execSQL("DROP TABLE Modules;");
//        myBase.execSQL("DROP TABLE Classes;");
//        myBase.execSQL("DROP TABLE Grades;");

       MediaPlayer mp = MediaPlayer.create(this, R.raw.cheer);
// preps DB tables
//        myBase.execSQL("CREATE TABLE if not exists Assignments2(title TEXT, code TEXT, dueDate TEXT, notes TEXT, id INT, hID INT, tfID INT, feID INT);");
//        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
//        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
//        myBase.e//        myBase.execSQL("CREATE TABLE if not exists Assignments2(title TEXT, code TEXT, dueDate TEXT, notes TEXT, id INT, hID INT, tfID INT, feID INT);");
////        myBase.execSQL("CREATE TABLE if not exists Modules(title TEXT, code TEXT, leader TEXT, notes TEXT);");
////        myBase.execSQL("CREATE TABLE if not exists Classes(code TEXT, type TEXT, lecturer TEXT, notes TEXT, location TEXT, day TEXT, start TEXT, finish TEXT, id INT);");
////        myBase.execSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");xecSQL("CREATE TABLE if not exists Grades(code TEXT, target INT, firstWeight INT, firstObtained INT, secondWeight INT, secondObtained INT, thirdWeight INT, thirdObtained INT,fourthWeight INT, fourthObtained INT, fifthWeight INT, fifthObtained INT);");


        AppCompatButton letsGoButton = (AppCompatButton) findViewById(R.id.buttonLetsGo);
        letsGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Calendar.class));
                mp.start();
            }
        });
    }

}