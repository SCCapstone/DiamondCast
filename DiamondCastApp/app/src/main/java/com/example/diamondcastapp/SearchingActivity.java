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


import com.google.android.gms.common.data.DataHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchingActivity extends AppCompatActivity {

    private EditText searchField;

    private ImageButton enterSearchField;

    private RecyclerView searchResultList;

    private DatabaseReference databaseReference;

    private SearchAdapter adapter;

    private ArrayList<Contractor> list;

    public Contractor selectedContractor;

    private Button searchSelection;

    private String selectedContractorUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

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
                    Contractor contractor = dataSnapshot.getValue(Contractor.class);
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
                String toScheduleWith = "Schedule with: "+selectedContractor.getFirstName();
                searchSelection.setText(toScheduleWith);
                Log.v("CLICKED", "Clicking on item(" + position + ", " + selectedContractor.getFirstName()+ ")");
            }
        });
        searchSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAppointmentCalendarActivity();
            }
        });


    }

    private void goToAppointmentCalendarActivity() {
        Intent intent = new Intent(this, AppointmentCalendarActivity.class);
        intent.putExtra("selectedContractor", selectedContractor.getFirstName());
        intent.putExtra("selectedContractorUID", selectedContractorUID);
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