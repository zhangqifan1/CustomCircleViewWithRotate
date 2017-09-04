package com.example.anadministrator.customcircleviewwithrotate;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Circle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circle = (Circle) findViewById(R.id.circle);
    }
    public void onClick(View view) {
        circle.setColor(Color.BLUE);
    }

    public void add(View view) {
        circle.speed();
    }

    public void slow(View view) {
        circle.slowDown();
    }


    public void pauseOrStart(View view) {
        circle.pauseOrStart();
    }
}
