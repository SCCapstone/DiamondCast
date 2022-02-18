package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class ClientHomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home_screen);
        Button appointmentScheduler = (Button) findViewById(R.id.goToAppointmentBtn);

        //Navigation Menu
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_menu);
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        appointmentScheduler.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToAppointmentServiceSelectionActivity(); }
        });
        ImageButton SearchBTN = (ImageButton) findViewById(R.id.goToSearchBtn);
        SearchBTN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToSearchActivity(); }
        });
        ImageButton profileBTN = (ImageButton) findViewById(R.id.goToProfileBtn);
        profileBTN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToProfileActivity(); }
        });
    }
    public void goToAppointmentSchedulerActivity() {
        Intent intent = new Intent(this, AppointmentSchedulerActivity.class);
        startActivity(intent);
    }
    public void goToAppointmentServiceSelectionActivity() {
        Intent intent = new Intent(this, AppointmentServiceSelectionActivity.class);
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