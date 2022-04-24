package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.diamondcastapp.databinding.ActivityClientHomeScreenBinding;
import com.example.diamondcastapp.databinding.ActivitySearchingAgentBinding;
import com.example.diamondcastapp.databinding.ActivitySearchingBinding;
import com.google.android.gms.common.data.DataHolder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchingAgentActivity extends NavigationDrawerActivity {

    private EditText searchField;
    private ImageButton enterSearchField;
    private RecyclerView searchResultList;
    private DatabaseReference databaseReference;
    private SearchAgentAdapter adapter;
    private ArrayList<Agent> list;
    public Agent selectedAgent;
    private Button searchSelection;
    private String selectedAgentID;
    private String clientNameForAppointment;
    private User clientUserMakingAppointment;
    private ImageButton goToMapButton;
    public Boolean boolPicked = false;
    private Button switchSearchType;


    ActivitySearchingAgentBinding activitySearchingAgentBinding;


    //private String selectedContractorUID;

    private ArrayList<String> selectedAgentServicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchingAgentBinding = ActivitySearchingAgentBinding.inflate(getLayoutInflater());
        setContentView(activitySearchingAgentBinding.getRoot());
        allocateActivityTitle("Searching");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // finding all the views that will need to be set
        switchSearchType = findViewById(R.id.switch_search_type);
        searchSelection = findViewById(R.id.search_selection_btn);
        searchField = findViewById(R.id.search_bar);
        enterSearchField = findViewById(R.id.searchImageButton);
        searchResultList = findViewById(R.id.searchResults);
        searchResultList.setHasFixedSize(true);
        searchResultList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new SearchAgentAdapter(list, this);

        searchResultList.setAdapter(adapter);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        dReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = fUser.getUid();
        dReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if(dataSnapshot.getKey().equals(userID))
                        clientUserMakingAppointment = dataSnapshot.getValue(User.class);
                }
                if (clientUserMakingAppointment != null)
                    clientNameForAppointment = clientUserMakingAppointment.getFirstName()+" "+clientUserMakingAppointment.getLastName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Agents");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String contractorID = dataSnapshot.getKey();
                    Agent agent = dataSnapshot.getValue(Agent.class);
                    agent.setId(contractorID);
                    list.add(agent);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        enterSearchField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter(searchField.getText().toString());

            }
        });
        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = searchResultList.getChildAdapterPosition(v);
                selectedAgent = list.get(position);
                selectedAgentID = selectedAgent.getId();
                String toScheduleWith = "Schedule with: " + selectedAgent.getFirstName();
                selectedAgentServicesList = selectedAgent.getServicesOffered();
                searchSelection.setText(toScheduleWith);
                Log.v("CLICKED", "Clicking on item(" + position + ", " + selectedAgent.getFirstName() + ")");
                boolPicked = true;
            }
        });
        searchSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boolPicked == true) {
                    goToAppointmentConfirmationActivity();
                } else {
                    Snackbar.make(searchSelection, "You must pick a valid agent", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        switchSearchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchingActivity();
            }
        });


    }

    private void goToSearchingActivity() {
        Intent intent = new Intent(this, SearchingActivity.class);
        startActivity(intent);
    }

    //goes to AppointmentConfirmationActivity and sends extra data to activity
    private void goToAppointmentConfirmationActivity() {
        Intent intent = new Intent(this, AppointmentConfirmationActivity.class);
        intent.putExtra("clientNameForAppointment", clientNameForAppointment);
        intent.putExtra("selectedAppointmentWithID", selectedAgentID);
        intent.putExtra("selectedContractor", selectedAgent.getFirstName()+ " " +selectedAgent.getLastName());
        intent.putExtra("selectedType", "Agent");
        //intent.putExtra("selectedContractorUID", selectedContractorUID);
        intent.putStringArrayListExtra("selectedContractorServicesList", selectedAgentServicesList);
        startActivity(intent);
    }

    //filters through list of contractors and updates adapter with contractors that have name matching search string

    void filter(String text) {
        ArrayList<Agent> temp = new ArrayList<>();
        for (Agent agent : list) {
            String cName = agent.getFirstName().toLowerCase(Locale.ROOT);
            if (cName.contains(text.toLowerCase(Locale.ROOT))) {
                temp.add(agent);
            }
        }
        //update recyclerview
        adapter = new SearchAgentAdapter(temp, this);
        searchResultList.setAdapter(adapter);
    }
}