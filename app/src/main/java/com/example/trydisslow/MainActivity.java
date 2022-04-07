package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private final static String NOT_SUPPORTED_MESSAGE = "Sorry, sensor not available for this device.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        SQLiteDatabase myBase = getApplicationContext().openOrCreateDatabase("Names.db", 0, null);
//        myBase.execSQL("DROP TABLE ASSIGNMENTS2;");
//        myBase.execSQL("DROP TABLE Modules;");
//        myBase.execSQL("DROP TABLE Classes;");
//        myBase.execSQL("DROP TABLE Grades;");

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cheer);

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