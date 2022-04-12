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
import com.example.diamondcastapp.databinding.ActivitySearchingBinding;
import com.google.android.gms.common.data.DataHolder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchingActivity extends NavigationDrawerActivity {

    private EditText searchField;
    private ImageButton enterSearchField;
    private RecyclerView searchResultList;
    private DatabaseReference databaseReference;
    private SearchAdapter adapter;
    private ArrayList<Contractor> list;
    public Contractor selectedContractor;
    private Button searchSelection;
    private String selectedContractorID;
    public Boolean boolPicked =false;
    private ImageButton goToMapButton;
    private Button switchSearchType;



    ActivitySearchingBinding activitySearchingBinding;

    //private String selectedContractorUID;

    private ArrayList<String> selectedContractorServicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchingBinding = ActivitySearchingBinding.inflate(getLayoutInflater());
        setContentView(activitySearchingBinding.getRoot());
        allocateActivityTitle("Searching");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switchSearchType = findViewById(R.id.switch_search_type);
        searchSelection = findViewById(R.id.search_selection_btn);
        searchField = findViewById(R.id.search_bar);
        enterSearchField = findViewById(R.id.searchImageButton);
        searchResultList = findViewById(R.id.searchResults);
        searchResultList.setHasFixedSize(true);
        searchResultList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new SearchAdapter(list, this);

        searchResultList.setAdapter(adapter);

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
                Log.v("CLICKED", "Clicking on item(" + position + ", " + selectedContractor.getFirstName()+ ")");
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
                    Snackbar.make(searchSelection, "You must pick a valid contractor", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        switchSearchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearchingAgentActivity();
            }
        });



    }

    private void goToSearchingAgentActivity() {
        Intent intent = new Intent(this, SearchingAgentActivity.class);
        startActivity(intent);
    }

    private void goToAppointmentConfirmationActivity() {
        Intent intent = new Intent(this, AppointmentConfirmationActivity.class);
        intent.putExtra("selectedAppointmentWithID", selectedContractorID);
        intent.putExtra("selectedContractor", selectedContractor.getFirstName()+ " " +selectedContractor.getLastName());
        intent.putExtra("selectedType", "Contractor");
        //intent.putExtra("selectedContractorUID", selectedContractorUID);
        intent.putStringArrayListExtra("selectedContractorServicesList", selectedContractorServicesList);
        startActivity(intent);
    }

    void filter(String text){
        ArrayList<Contractor> temp = new ArrayList<>();
        for(Contractor contractor: list){
            if(contractor.getFirstName().contains(text)){
                temp.add(contractor);
            }
        }
        //update recyclerview
        adapter = new SearchAdapter(temp, this);
        searchResultList.setAdapter(adapter);
    }

   /* private void firebaseUserSearch() {
        Query query=databaseReference; //.equalto ?
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();
        FirebaseRecyclerAdapter<User, SearchResultsViewHolder> firebaseRecyclerAdapter;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, SearchResultsViewHolder>(options){
            @Override
            protected void onBindViewHolder(@NonNull SearchResultsViewHolder holder, int position, @NonNull User model) {
                holder.setDetails(model.getFirstName(), model.getLastName());
            }
            @NonNull
            @Override
            public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_result_list, parent, false);
                return new SearchResultsViewHolder(view);
            }
        };
        //searchResultList.setAdapter(firebaseRecyclerAdapter);
    }
    //view holder class
    public class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        View view;
        public SearchResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setDetails(String name, String desc) {
            TextView user_name = findViewById(R.id.search_result_name);
            TextView description = findViewById(R.id.search_result_description);
            user_name.setText(name);
            description.setText(desc);
        }
    } */

}
   /* private void firebaseUserSearch() {
        Query query=databaseReference; //.equalto ?

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();
        FirebaseRecyclerAdapter<User, SearchResultsViewHolder> firebaseRecyclerAdapter;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, SearchResultsViewHolder>(options){
            @Override
            protected void onBindViewHolder(@NonNull SearchResultsViewHolder holder, int position, @NonNull User model) {
                holder.setDetails(model.getFirstName(), model.getLastName());

            }

            @NonNull
            @Override
            public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_result_list, parent, false);
                return new SearchResultsViewHolder(view);
            }


        };
        //searchResultList.setAdapter(firebaseRecyclerAdapter);
    }

    //view holder class

    public class SearchResultsViewHolder extends RecyclerView.ViewHolder {

        View view;

        public SearchResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setDetails(String name, String desc) {
            TextView user_name = findViewById(R.id.search_result_name);
            TextView description = findViewById(R.id.search_result_description);

            user_name.setText(name);
            description.setText(desc);

        }

    } */

