package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AppointmentServiceSelectionActivity extends AppCompatActivity {
    Button confirmServiceSelectionBtn;
    ChipGroup chipGroupServiceSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_service_selection);

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
     //   chipGroupServiceSelection = findViewById(R.id.chipGroupServiceSelection);
        //for(int i = 0; i < selectedContractorServiceList; i++) {
            Chip chip = new Chip(this);
       //     chip.setText(selectedContractorServiceList.get(i));
            chipGroupServiceSelection.addView(chip);
       // }

    }
}