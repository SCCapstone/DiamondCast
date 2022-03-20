package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class AppointmentConfirmationActivity extends AppCompatActivity {
    TextView displaySelectedDate;
    Button confirmAppointmentBtn;
    TextView displaySelectedTime;
    TextView displaySelectedContractor;
    TextView displaySelectedServices;
    String selectedContractor;
    String selectedDate;
    int selectedHour;
    int selectedMinute;
    ArrayList<String> selectedServicesList;
    ArrayList<String> appointmentList;
    Appointment appointment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_confirmation);
        //get data passed from previous activity
        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("selectedDate");
        selectedHour = intent.getIntExtra("hour", 0);
        selectedMinute = intent.getIntExtra("minute", 0);
        selectedContractor = intent.getStringExtra("selectedContractor");
        selectedServicesList = intent.getStringArrayListExtra("selectedServices");


        //setting views and buttons to correct id
        displaySelectedDate =  findViewById(R.id.displaySelectedDateNew);
        confirmAppointmentBtn = findViewById(R.id.confirm_appointment_button);
        displaySelectedContractor = findViewById(R.id.displaySelectedContractorConfirm);
        displaySelectedServices = findViewById(R.id.displaySelectedServicesConfirm);

        String selectedServicesDisplayString = String.join(", ", selectedServicesList);

        displaySelectedServices.setText(selectedServicesDisplayString);

        displaySelectedDate.setText(selectedDate);
        displaySelectedTime = findViewById(R.id.displaySelectedTimeNew);
        String selectedTimeDisplay = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
        displaySelectedTime.setText(selectedTimeDisplay);
        String displaySelectedContractorString = "Appointment with: "+selectedContractor;
        displaySelectedContractor.setText(displaySelectedContractorString);

        String selectedService = "service choice";

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        appointment = new Appointment("Appointment with: "+selectedContractor, selectedDate, selectedTimeDisplay, selectedServicesList, true);

        confirmAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Appointments").child(currentUserId).setValue(appointment)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    goToHomeScreenActivity();
                                } else{
                                    Snackbar.make(findViewById(R.id.confirm_appointment_button), "Failed to submit appointment", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });





    }
    public void goToHomeScreenActivity() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Clients")
                .child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null && user.getUserType() == UserType.Client) {
                    goToClientHomeScreenActivity();
                } else if (user != null && user.getUserType() == UserType.Agent) {
                    goToAgentHomeScreenActivity();
                } else if (user != null && user.getUserType() == UserType.Contractor) {
                    goToContractorHomeScreenActivity();
                } else {
                    Snackbar.make(findViewById(R.id.loginEnter), "Something went wrong", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(findViewById(R.id.loginEnter), "An error has occurred: " + error, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    private void goToClientHomeScreenActivity() {
        Intent intent = new Intent(this, ClientHomeScreenActivity.class);
        intent.putExtra("appointment", appointment);
        startActivity(intent);
    }

    private void goToAgentHomeScreenActivity() {
        Intent intent = new Intent(this, AgentHomeScreenActivity.class);
        startActivity(intent);
    }

    private void goToContractorHomeScreenActivity() {
        Intent intent = new Intent(this, ContractorHomeScreenActivity.class);
        startActivity(intent);
    }


}