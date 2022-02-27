package com.example.diamondcastapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AppointmentTimeAndServiceChoiceActivity extends AppCompatActivity {
    Button selectTimeButton;
    int hour, minute;
    String selectedDate;
    TextView displaySelectedContractor;
    TextView displaySelectedDate;
    ArrayList<Contractor> listContractorWithName;
    ArrayList<String> selectedContractorServicesList;
    String selectedContractor;
    ArrayList<String> selectedServicesList;
    TextView displaySelectedServices;


    @RequiresApi(api = Build.VERSION_CODES.O)
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
        selectedContractorServicesList = intent.getStringArrayListExtra("selectedContractorServicesList");

        displaySelectedDate = findViewById(R.id.displaySelectedDateServiceChoice);
        displaySelectedContractor = findViewById(R.id.displaySelectedContractorName);

        displaySelectedDate.setText(selectedDate);
        displaySelectedContractor.setText(selectedContractor);

        confirmServiceSelectionBtn = findViewById(R.id.confirmServiceSelectionBtn);
        chipGroup = findViewById(R.id.chipGroup);

        selectedServicesList = new ArrayList<>();

        confirmServiceSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    List<Integer> selectedChipIds = chipGroup.getCheckedChipIds();
                    for (int i = 0; i < selectedChipIds.size(); i++) {
                        Chip chip = chipGroup.findViewById(selectedChipIds.get(i));
                        if (chip.isChecked()) {
                            selectedServicesList.add(chip.getText().toString());

                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), AppointmentConfirmationActivity.class);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("selectedContractor", selectedContractor);
                    intent.putExtra("hour", hour);
                    intent.putExtra("minute", minute);
                    intent.putStringArrayListExtra("selectedServices", selectedServicesList);
                    startActivity(intent);

                }
            });

            // need to get contractor choice from search activity
            // selectedContractor =
            // ArrayList<String> selectedContractorServiceList = selectedContractor.getServicesOffered()

            // below way wont work cant figure it out
          /*  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Contractors").child();

            Query findContractorsWithName = databaseReference;
            // list of contractors with name equal to that of selectedContractor
            listContractorWithName = new ArrayList<>();
            findContractorsWithName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                   if (snapshot.child("firstName").toString().equalsIgnoreCase(selectedContractor)) {
                       listContractorWithName.add(snapshot.getValue(Contractor.class));
                   }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });*/

            for(int i = 0; i < selectedContractorServicesList.size(); i++) {
                Chip chip = new Chip(this);
                ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
                chip.setChipDrawable(drawable);
                chip.setText(selectedContractorServicesList.get(i));
                chipGroup.addView(chip);
            }

            chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(ChipGroup group, int checkedId) {
                    Chip chip = group.findViewById(checkedId);
                    selectedServicesList.add(chip.getText().toString());
                }
            });

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