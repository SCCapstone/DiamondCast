package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AppointmentDetailsActivity extends AppCompatActivity {
    TextView displaySelectedDate;
    Button confirmAppointmentBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selectedDate");

        displaySelectedDate = (TextView) findViewById(R.id.displaySelectedDate);
        displaySelectedDate.setText(selectedDate);

        confirmAppointmentBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v1) { goToHomeScreenActivity(); }
        });

    }

}