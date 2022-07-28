package com.example.diamondcastapp;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.diamondcastapp.databinding.ActivityAgentHomeScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AgentHomeScreenActivity extends NavigationDrawerActivity {

    private HomeScreenAppointmentAdapter adapter;
    private RecyclerView homeScreenApptList;
    public Appointment createdAppointment;
    private ArrayList<Appointment> list;
    private DatabaseReference databaseReference, cancellationReference;
    private AppointmentList appointmentList;
    ActivityAgentHomeScreenBinding activityAgentHomeScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bind the current activity with navigation drawer
        activityAgentHomeScreenBinding = ActivityAgentHomeScreenBinding
                .inflate(getLayoutInflater());
        setContentView(activityAgentHomeScreenBinding.getRoot());
        allocateActivityTitle("Home");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Appointment RecyclerView

        Intent intent = getIntent();
        createdAppointment = (Appointment) intent.getSerializableExtra("appointment");

        // setting up adapter
        homeScreenApptList = findViewById(R.id.appointmentResultTwo);
        homeScreenApptList.setHasFixedSize(true);
        homeScreenApptList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new HomeScreenAppointmentAdapter(list, this);
        homeScreenApptList.setAdapter(adapter);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        appointmentList = new AppointmentList();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Appointments");
        cancellationReference = FirebaseDatabase.getInstance().getReference().child("CancellationNotifications");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(currentUserId)) {
                        appointmentList = dataSnapshot.getValue(AppointmentList.class);
                        for (Appointment appointment : appointmentList.getAppointmentList())
                            list.add(appointment);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AgentHomeScreenActivity.this,
                        "An error has occurred: "+error, Toast.LENGTH_SHORT).show();
            }
        });

        cancellationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(currentUserId)) {
                        Toast.makeText(AgentHomeScreenActivity.this, "An appointment was cancelled", Toast.LENGTH_SHORT).show();
                        cancellationReference.child(currentUserId).setValue(null);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AgentHomeScreenActivity.this,
                        "An error has occurred: "+error, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int position = homeScreenApptList.getChildAdapterPosition(v);
               Appointment toRemove = list.get(position);
               String contractorAgentId = toRemove.getAppointmentWithId();
               Notification notification = new Notification("Appointment at " + toRemove.getTime() + " " + toRemove.getDate() + " cancelled");
               FirebaseDatabase.getInstance().getReference("CancellationNotifications").child(contractorAgentId).child("Notifications").push().setValue(notification);
               list.remove(toRemove);
               FirebaseDatabase.getInstance().getReference("Appointments")
                       .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("appointmentList").setValue(list);
               refreshHomeScreenActivity();
            }
        });
    }

    //Makes deleting work, bugged out before, especially if you had 3+ appointments
    public void refreshHomeScreenActivity() {
        Intent intent = new Intent(this, AgentHomeScreenActivity.class);
        startActivity(intent);
    }

    public void goToProfileActivity() {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
    }
}