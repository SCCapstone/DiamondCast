package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.diamondcastapp.databinding.ActivityClientHomeScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ClientHomeScreenActivity extends NavigationDrawerActivity {
    private HomeScreenAppointmentAdapter adapter;
    private RecyclerView homeScreenApptList;
    private ArrayList<Appointment> list;
    private DatabaseReference databaseReference;
    private AppointmentList appointmentList;
    public Appointment createdAppointment;
    ActivityClientHomeScreenBinding activityClientHomeScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //bind the current activity with navigation drawer
        activityClientHomeScreenBinding = ActivityClientHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(activityClientHomeScreenBinding.getRoot());
        allocateActivityTitle("Home");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        createdAppointment = (Appointment) intent.getSerializableExtra("appointment");

        // setting up adapter
        homeScreenApptList = findViewById(R.id.upcoming_appts_list);
        homeScreenApptList.setHasFixedSize(true);
        homeScreenApptList.setLayoutManager(new LinearLayoutManager(this));
        appointmentList = new AppointmentList();
        list = new ArrayList<>();
        adapter = new HomeScreenAppointmentAdapter(list, this);
        homeScreenApptList.setAdapter(adapter);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Appointments");

        // retrieving appointment list (instead of appointment!) from database and separating into individual appointments to display on home screen.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(currentUserId)) {
                        appointmentList = dataSnapshot.getValue(AppointmentList.class);
                        for(Appointment appointment : appointmentList.getAppointmentList()) {
                            list.add(appointment);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ClientHomeScreenActivity.this, "An error has occurred: "+error,
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Buttons
        Button appointmentScheduler = (Button) findViewById(R.id.goToAppointmentBtn);
        appointmentScheduler.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAppointmentSchedulerActivity();
            }
        });

        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = homeScreenApptList.getChildAdapterPosition(v);
                Toast.makeText(ClientHomeScreenActivity.this, "Clicked on position " + position, Toast.LENGTH_SHORT).show();
                list.remove(list.get(position));
                FirebaseDatabase.getInstance().getReference("Appointments")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(list);
            }
        });

    }

    public void goToAppointmentSchedulerActivity() {
        Intent intent = new Intent(this, AppointmentConfirmationActivity.class);
        startActivity(intent);
    }
}