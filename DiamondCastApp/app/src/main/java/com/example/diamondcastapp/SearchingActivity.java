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
import com.example.diamondcastapp.databinding.ActivitySearchingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Locale;

public class SearchingActivity extends NavigationDrawerActivity {
    private EditText searchField;
    private ImageButton enterSearchField;
    private RecyclerView searchResultList;
    private DatabaseReference databaseReference;
    private SearchAdapter adapter;
    private ArrayList<Contractor> list;
    private Button switchSearchType, searchSelection, sameSearchType;
    private String selectedContractorID, clientNameForAppointment;
    private User clientUserMakingAppointment;
    public Boolean boolPicked =false;
    public Contractor selectedContractor;
    private ArrayList<String> selectedContractorServicesList;
    ActivitySearchingBinding activitySearchingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchingBinding = ActivitySearchingBinding.inflate(getLayoutInflater());
        setContentView(activitySearchingBinding.getRoot());
        allocateActivityTitle("Searching");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switchSearchType = findViewById(R.id.switch_search_type);
        searchSelection = findViewById(R.id.search_selection_btn);
        sameSearchType = findViewById(R.id.same_search_type);
        searchField = findViewById(R.id.search_bar);
        enterSearchField = findViewById(R.id.searchImageButton);
        searchResultList = findViewById(R.id.searchResults);
        searchResultList.setHasFixedSize(true);
        searchResultList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new SearchAdapter(list, this);

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
                Toast.makeText(SearchingActivity.this,
                        "Something went wrong. User isn't a known user type.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Contractors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String contractorID = dataSnapshot.getKey();
                    Contractor contractor = dataSnapshot.getValue(Contractor.class);
                    contractor.setId(contractorID);
                    list.add(contractor);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchingActivity.this,
                        "Something went wrong. User isn't a known user type.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        enterSearchField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                filter(searchField.getText().toString());

            }
        });

        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = searchResultList.getChildAdapterPosition(v);
                selectedContractor = list.get(position);
                selectedContractorID = selectedContractor.getId();
                String toScheduleWith = "Schedule with: "+selectedContractor.getFirstName();
                selectedContractorServicesList = selectedContractor.getServicesOffered();
                searchSelection.setText(toScheduleWith);
                Log.v("CLICKED", "Clicking on item(" + position + ", "
                        + selectedContractor.getFirstName()+ ")");
                boolPicked = true;
            }
        });

        searchSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boolPicked == true) {
                    goToAppointmentConfirmationActivity();
                }
                else{
                    Toast.makeText(SearchingActivity.this,
                            "You must pick a valid contractor", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchSearchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchingAgentActivity();
            }
        });

        sameSearchType.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToSameActivity(); }
        }));
    }

    private void goToSearchingAgentActivity() {
        Intent intent = new Intent(this, SearchingAgentActivity.class);
        startActivity(intent);
    }

    private void goToSameActivity() {
        Intent intent = new Intent(this, SearchingActivity.class);
        startActivity(intent);
    }

    private void goToAppointmentConfirmationActivity() {
        Intent intent = new Intent(this, AppointmentConfirmationActivity.class);
        intent.putExtra("clientNameForAppointment", clientNameForAppointment);
        intent.putExtra("selectedAppointmentWithID", selectedContractorID);
        intent.putExtra("selectedContractor", selectedContractor.getFirstName()+ " " +selectedContractor.getLastName());
        intent.putExtra("selectedType", "Contractor");
        intent.putStringArrayListExtra("selectedContractorServicesList", selectedContractorServicesList);
        startActivity(intent);
    }

    //filters through list of contractors and updates adapter with contractors that have name
    //matching search string
    void filter(String text){
        ArrayList<Contractor> temp = new ArrayList<>();
        for(Contractor contractor: list){
            String cName = contractor.getFirstName().toLowerCase(Locale.ROOT);
            if(cName.contains(text.toLowerCase(Locale.ROOT))){
                temp.add(contractor);
            }
        }

        //update recyclerview
        adapter = new SearchAdapter(temp, this);
        searchResultList.setAdapter(adapter);
    }
}