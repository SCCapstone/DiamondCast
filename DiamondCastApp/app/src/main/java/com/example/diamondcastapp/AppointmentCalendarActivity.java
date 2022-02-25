package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Locale;

public class AppointmentCalendarActivity extends AppCompatActivity {
    String selectedDate;
    CalendarView calendar;
    TextView date_view;
    String selectedContractor;
    TextView displaySelectedContractor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_calendar);
        Intent intent = getIntent();
        selectedContractor = intent.getStringExtra("selectedContractor");

        displaySelectedContractor = findViewById(R.id.selected_contractor_display_calendar);
        displaySelectedContractor.setText(selectedContractor);

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
                                        = (month + 1) + "-"
                                        + dayOfMonth + "-" + year;

                                date_view.setText(selectedDate);
                            }
                        });
        //  calendar.getDate();




    }



    public void goToTimeAndServiceChoicePage(View view) {
        Intent intent = new Intent(this, AppointmentTimeAndServiceChoiceActivity.class);
        intent.putExtra("selectedDate", selectedDate);
        intent.putExtra("selectedContractor", selectedContractor);
        startActivity(intent);
    }

    public void goToPreviousPage(View view) {
        Intent intent = new Intent(this, ClientHomeScreenActivity.class);
        startActivity(intent);
    }
}
