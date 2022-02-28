package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.diamondcastapp.databinding.ActivityAgentHomeScreenBinding;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NonNls;

public class AgentHomeScreenActivity extends nav_menu_base {

    ActivityAgentHomeScreenBinding activityAgentHomeScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAgentHomeScreenBinding = activityAgentHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(activityAgentHomeScreenBinding.getRoot());
        allocateActivityTitle("Home Screen Agent");

        ImageButton profileBTN = (ImageButton) findViewById(R.id.goToProfileBtn);
        profileBTN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToProfileActivity(); }
        });
        /*
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    Toast.makeText(AgentHomeScreenActivity.this, "home", Toast.LENGTH_SHORT).show();
                }
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
            return true;
            }
        });
        */
    }
    public void goToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}