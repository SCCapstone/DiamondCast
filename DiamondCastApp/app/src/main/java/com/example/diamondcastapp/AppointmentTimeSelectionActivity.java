package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Locale;

public class AppointmentTimeSelectionActivity extends AppCompatActivity {

    Button selectTimeButton;
    int hour, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_time_selection);
        selectTimeButton = findViewById(R.id.chooseAppointmentTimeButton);
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute,true);

        timePickerDialog.setTitle("SelectTime");
        timePickerDialog.show();

    }
}