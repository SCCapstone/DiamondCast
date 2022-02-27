package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AgentHomeScreenActivity extends AppCompatActivity {

    private RecyclerView resultList;
    private DatabaseReference databaseReference;
    private HomeScreenAdapter homeScreenAdapter;
    private ArrayList<Appointment> userAppt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_home_screen);

        //Appointment RecyclerView
        resultList = findViewById(R.id.appointmentResultTwo);
        resultList.setHasFixedSize(true);
        resultList.setLayoutManager(new LinearLayoutManager(this));
        userAppt = new ArrayList<>();
        homeScreenAdapter = new HomeScreenAdapter(userAppt, this);
        resultList.setAdapter(homeScreenAdapter);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Appointments");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(currentUserId)) {
                        Appointment appointment = dataSnapshot.getValue(Appointment.class);
                        userAppt.add(appointment);
                    }
                }
                homeScreenAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        ImageButton profileBTN = (ImageButton) findViewById(R.id.goToProfileBtn);
        profileBTN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToProfileActivity(); }
        });
    }
    public void goToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}