package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;


public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation_drawer,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId())  {
            case R.id.nav_home:
                startActivity(new Intent(this, ClientHomeScreenActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_search:
                startActivity(new Intent(this, SearchingActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_appointment:
                startActivity(new Intent(this, AppointmentConfirmationActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_messaging:
                startActivity(new Intent(this, InboxActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                overridePendingTransition(0,0);
                break;
        }
        return false;
    }

    protected void allocateActivityTitle(String title) {
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

}