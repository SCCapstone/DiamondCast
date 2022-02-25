package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.Locale;

public class AppointmentTimeAndServiceChoiceActivity extends AppCompatActivity {
    Button selectTimeButton;
    int hour, minute;
    String selectedDate;
    TextView displaySelectedContractor;
    TextView displaySelectedDate;
    String selectedContractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_time_and_service_choice);
        Button confirmServiceSelectionBtn;
        ChipGroup chipGroup;
        selectTimeButton = findViewById(R.id.chooseAppointmentTimeButton);
        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("selectedDate");
        selectedContractor = intent.getStringExtra("selectedContractor");

        displaySelectedDate = findViewById(R.id.displaySelectedDateServiceChoice);
        displaySelectedContractor = findViewById(R.id.displaySelectedContractorServiceChoice);

        displaySelectedDate.setText(selectedDate);
        displaySelectedContractor.setText(selectedContractor);

        confirmServiceSelectionBtn = findViewById(R.id.confirmServiceSelectionBtn);

        confirmServiceSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AppointmentConfirmationActivity.class);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("selectedContractor", selectedContractor);
                    intent.putExtra("hour", hour);
                    intent.putExtra("minute", minute);
                    startActivity(intent);

                }
            });

            // need to get contractor choice from search activity
            // selectedContractor =
            // ArrayList<String> selectedContractorServiceList = selectedContractor.getServicesOffered()
            chipGroup = findViewById(R.id.chipGroup);

            for(int i = 0; i < 8; i++) {
                Chip chip = new Chip(this);
                ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
                chip.setChipDrawable(drawable);
                chip.setText("whaththathaht");
                chipGroup.addView(chip);
            }

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

        timePickerDialog.setTitle("SelectTime");
        timePickerDialog.show();

    }
    public void passExtras(){
        Intent detailConfirmationIntent = new Intent(this, AppointmentConfirmationActivity.class);
        detailConfirmationIntent.putExtra("selectedDate", selectedDate);
        detailConfirmationIntent.putExtra("hour",hour);
        detailConfirmationIntent.putExtra("minute", minute);

    }
}