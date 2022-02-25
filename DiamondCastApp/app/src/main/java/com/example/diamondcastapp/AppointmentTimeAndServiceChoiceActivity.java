package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

public class AppointmentTimeAndServiceChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_time_and_service_choice);
        Button confirmServiceSelectionBtn;
        ChipGroup chipGroup;

        confirmServiceSelectionBtn = findViewById(R.id.confirmServiceSelectionBtn);

        confirmServiceSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AppointmentDetailsActivity.class);
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

}