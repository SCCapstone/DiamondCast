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

public class AppointmentSchedulerActivity extends AppCompatActivity {
    String selectedDate = "";
    CalendarView calendar;
    TextView date_view;
    Button selectTimeButton;
    int hour, minute;
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
                                        = (month + 1) + "-"
                                        + dayOfMonth + "-" + year;

                                date_view.setText(selectedDate);
                            }
                        });
      //  calendar.getDate();



        selectTimeButton = findViewById(R.id.chooseAppointmentTimeBtn);
    }
        public void openTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute){
                hour = selectedHour;
                minute = selectedMinute;
                selectTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute,true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

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
