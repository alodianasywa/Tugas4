package com.example.myfirebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class project3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project3);
    }
    public void openRegistration(View v){
        Intent intent = new Intent(project3.this, DBCreateActivity.class);
        startActivity(intent);
    }
    public void openCarList(View v){
        Intent intent = new Intent(project3.this, CarList.class);
        startActivity(intent);
    }
    public void openGuestList(View v) {
        Intent intent = new Intent(project3.this, DBReadActivity.class);
        startActivity(intent);
    }
    public void openAboutUs(View v) {
        Intent intent = new Intent(project3.this, AboutUs.class);
        startActivity(intent);
    }
}