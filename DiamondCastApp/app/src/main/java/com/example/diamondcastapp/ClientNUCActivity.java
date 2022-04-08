package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.diamondcastapp.databinding.ActivityClientNucBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ClientNUCActivity extends NavigationDrawerActivity {
   ActivityClientNucBinding activityClientNUCBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //bind the current activity with navigation drawer
        activityClientNUCBinding = ActivityClientNucBinding.inflate(getLayoutInflater());
        setContentView(activityClientNUCBinding.getRoot());
        allocateActivityTitle("New User Checklist");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Buttons
        Button goToHomeScreen = (Button) findViewById(R.id.viewAppointmentsBtn);

        goToHomeScreen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToHomeScreenActivity(); }
        });
    }

    public void goToHomeScreenActivity() {
        startActivity(new Intent(this, ClientHomeScreenActivity.class));
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