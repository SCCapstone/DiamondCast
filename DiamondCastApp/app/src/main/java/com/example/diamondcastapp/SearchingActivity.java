package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchingActivity extends AppCompatActivity {
    private EditText searchField;
    private ImageButton enterSearchField;

    private RecyclerView searchResultList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        searchField = findViewById(R.id.search_bar);
        enterSearchField = findViewById(R.id.searchImageButton);
        searchResultList = findViewById(R.id.searchResults);
        searchResultList.setHasFixedSize(true);
        searchResultList.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query=databaseReference.child("Users").orderByChild("name"); //.equalto ?
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();

        enterSearchField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                firebaseUserSearch();
            }
        });

    }

    private void firebaseUserSearch() {
        Query query=databaseReference.child("Users").orderByChild("name"); //.equalto ?
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();
        FirebaseRecyclerAdapter<User, SearchResultsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, SearchResultsViewHolder>(options){
            @Override
            protected void onBindViewHolder(@NonNull SearchResultsViewHolder holder, int position, @NonNull User model) {
                holder.setDetails(model.getFirstName(), model.getLastName());
            }

            @NonNull
            @Override
            public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
        searchResultList.setAdapter(firebaseRecyclerAdapter);
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

    }
}