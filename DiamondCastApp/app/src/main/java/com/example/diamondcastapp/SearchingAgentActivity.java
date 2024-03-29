package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.diamondcastapp.databinding.ActivitySearchingAgentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Locale;

public class SearchingAgentActivity extends NavigationDrawerActivity {
    private EditText searchField;
    private ImageButton enterSearchField;
    private RecyclerView searchResultList;
    private DatabaseReference databaseReference;
    private SearchAgentAdapter adapter;
    private ArrayList<Agent> list;
    public Agent selectedAgent;
    private Button searchSelection, switchSearchType, sameSearchType;
    private String selectedAgentID, clientNameForAppointment;
    private User clientUserMakingAppointment;
    public Boolean boolPicked = false;
    ActivitySearchingAgentBinding activitySearchingAgentBinding;
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
        sameSearchType = findViewById(R.id.same_search_type);
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
                    clientNameForAppointment = clientUserMakingAppointment.getFirstName()+" "
                            +clientUserMakingAppointment.getLastName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchingAgentActivity.this,
                        "Something went wrong. User isn't a known user type.",
                        Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SearchingAgentActivity.this,
                        "Something went wrong. User isn't a known user type.",
                        Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SearchingAgentActivity.this, "You must pick a valid agent",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchSearchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchingActivity();
            }
        });

        sameSearchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToSameActivity(); }
        });
    }

    private void goToSearchingActivity() {
        Intent intent = new Intent(this, SearchingActivity.class);
        startActivity(intent);
    }

    private void goToSameActivity() {
        Intent intent = new Intent(this, SearchingAgentActivity.class);
        startActivity(intent);
    }

    //goes to AppointmentConfirmationActivity and sends extra data to activity
    private void goToAppointmentConfirmationActivity() {
        Intent intent = new Intent(this, AppointmentConfirmationActivity.class);
        intent.putExtra("clientNameForAppointment", clientNameForAppointment);
        intent.putExtra("selectedAppointmentWithID", selectedAgentID);
        intent.putExtra("selectedContractor", selectedAgent.getFirstName()+ " " +selectedAgent.getLastName());
        intent.putExtra("selectedType", "Agent");
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