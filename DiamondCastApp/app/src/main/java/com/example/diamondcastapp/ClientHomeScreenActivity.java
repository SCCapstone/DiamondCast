package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ClientHomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home_screen);

        Button appointmentScheduler = (Button) findViewById(R.id.goToAppointmentBtn);
        appointmentScheduler.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToAppointmentSchedulerActivity(); }
        });
        ImageButton SearchBTN = (ImageButton) findViewById(R.id.goToSearchBtn);
        SearchBTN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v1) { goToSearchActivity(); }
        });
    }
    public void goToAppointmentSchedulerActivity() {
        Intent intent = new Intent(this, AppointmentSchedulerActivity.class);
        startActivity(intent);
    }

    public void goToSearchActivity() {
        Intent intent = new Intent(this, SearchScreenActivity.class);
        startActivity(intent);
    }


}