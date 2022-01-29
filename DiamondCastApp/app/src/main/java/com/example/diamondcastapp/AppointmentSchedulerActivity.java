package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

public class AppointmentSchedulerActivity extends AppCompatActivity {
    String selectedDate = "";
    CalendarView calendar;
    TextView date_view;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_scheduler);

        calendar = (CalendarView) findViewById(R.id.calendar);
        date_view = (TextView) findViewById(R.id.setDate);

        calendar
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {


                                selectedDate
                                        = dayOfMonth + "-"
                                        + (month + 1) + "-" + year;

                                date_view.setText(selectedDate);
                            }
                        });
    }

    public void goToNextSchedulingPage(View view) {
        Intent intent = new Intent(this, AppointmentDetailsActivity.class);
        intent.putExtra("selectedDate", selectedDate);
        startActivity(intent);
    }

    public void goToPreviousPage(View view) {
        Intent intent = new Intent(this, ClientHomeScreenActivity.class);
        startActivity(intent);
    }
}
