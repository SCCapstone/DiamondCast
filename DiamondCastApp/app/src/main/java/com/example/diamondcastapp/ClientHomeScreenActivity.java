package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientHomeScreenActivity extends AppCompatActivity {
    private HomeScreenAppointmentAdapter adapter;
    private RecyclerView homeScreenApptList;
    public Appointment createdAppointment;
    private ArrayList<Appointment> list;
    private DatabaseReference databaseReference;

    private RecyclerView resultList;
    private DatabaseReference databaseReference;
    private HomeScreenAdapter homeScreenAdapter;
    private ArrayList<Appointment> userAppt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home_screen);


        Intent intent = getIntent();
        createdAppointment = (Appointment) intent.getSerializableExtra("appointment");

        // setting up adapter
        homeScreenApptList = findViewById(R.id.upcoming_appts_list);
        homeScreenApptList.setHasFixedSize(true);
        homeScreenApptList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new HomeScreenAppointmentAdapter(list, this);

        homeScreenApptList.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Appointments");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Appointment appointment = dataSnapshot.getValue(Appointment.class);
                    list.add(appointment);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button appointmentScheduler = (Button) findViewById(R.id.goToAppointmentBtn);
        appointmentScheduler.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToAppointmentServiceSelectionActivity(); }
        });
        ImageButton SearchBTN = (ImageButton) findViewById(R.id.goToSearchBtn);
        SearchBTN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToSearchActivity(); }
        });
        ImageButton profileBTN = (ImageButton) findViewById(R.id.goToProfileBtn);
        profileBTN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToProfileActivity(); }
        });
    }
    public void goToAppointmentSchedulerActivity() {
        Intent intent = new Intent(this, AppointmentCalendarActivity.class);
        startActivity(intent);
    }
    public void goToAppointmentServiceSelectionActivity() {
        Intent intent = new Intent(this, AppointmentCalendarActivity.class);
        startActivity(intent);
    }

    public void goToSearchActivity() {
        Intent intent = new Intent(this, SearchingActivity.class);
        startActivity(intent);
    }
    public void goToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

}