package com.lamra.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    Button start, stop, hold;
    Chronometer chronometer;
    long timeMilLis, timeStart, timeBuffer, timeUpdate = 0L;
    int sec, min, millis;
    Handler handler;
    boolean isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        hold = findViewById(R.id.hold);

        handler = new Handler();

        start.setOnClickListener(v -> {
            if (!isActive) {
                isActive = true;
                timeStart = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                chronometer.start();
            }
        });

        hold.setOnClickListener(v -> {
            if (isActive) {
                timeBuffer += timeMilLis;
                handler.removeCallbacks(runnable);
                chronometer.stop();
                isActive = false;
            }
        });

        stop.setOnClickListener(v -> {
            timeBuffer += timeMilLis;
            handler.removeCallbacks(runnable);
            chronometer.stop();
            isActive = false;
            timeMilLis = 0L;
            timeStart = 0L;
            timeBuffer = 0L;
            timeUpdate = 0L;
            sec = 0;
            min = 0;
            millis = 0;
            chronometer.setText("00:00:00");


        });

    }

    public Runnable runnable = new Runnable() {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public void run() {
            timeMilLis = SystemClock.uptimeMillis() - timeStart;
            timeUpdate = timeBuffer + timeMilLis;
            sec = Math.toIntExact((timeUpdate / 1000));
            min = sec / 60;
            millis = (int) (timeUpdate % 1000);

            chronometer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" + String.format("%02d", millis));
            handler.postDelayed(this, 60);
        }
    };
}