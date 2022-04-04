package com.example.trydisslow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import android.support.v7.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView temperaturelabel;
    private SensorManager mSensorManager;
    private Sensor mTemperature;
    private final static String NOT_SUPPORTED_MESSAGE = "Sorry, sensor not available for this device.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
//        TextView temp = (TextView)findViewById(R.id.tempText);
//        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH){
//            mTemperature= mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE); // requires API level 14.
//     //  temperaturelabel.setText("It is " + mTemperature. + " degrees right now.");
//        }
//        if (mTemperature == null) {
//            temperaturelabel.setText(NOT_SUPPORTED_MESSAGE);
//        }

        AppCompatButton letsGoButton = (AppCompatButton)findViewById(R.id.buttonLetsGo);
        letsGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Calendar.class));
            }
        });
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mSensorManager.unregisterListener(this);
//    }
//
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        float ambient_temperature = event.values[0];
//        temperaturelabel.setText("Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius));
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        // Do something here if sensor accuracy changes.
//    }
}
