package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ClientHomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home_screen);
    }
    public void goToAppointmentSchedulerActivity (View view) {
        Intent intent = new Intent(this, AppointmentScheduler.class);
        startActivity(intent);
    }
}