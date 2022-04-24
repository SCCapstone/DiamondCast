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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//class that sets up the navigation drawer
public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser fUser;
    DatabaseReference dReference;
    String userID, userType;
    DrawerLayout drawerLayout;

    private static int currentActivity;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation_drawer, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Remove appointment creation for Contractors / Agents
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Users");
        DatabaseReference current_userRef = usersRef.child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userType = dataSnapshot.child("userType").getValue(String.class);
                Log.d("TAG", userType);

                if (userType.equals("Contractor") || (userType.equals("Agent"))) {
                    navigationView.getMenu().findItem(R.id.nav_appointment).setVisible(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage());
            }
        };
        current_userRef.addListenerForSingleValueEvent(valueEventListener);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }
    //Go to activity based on item picked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
            switch (item.getItemId()) {
                case R.id.nav_home:
                    if (currentActivity != 1) {
                        goToHomeScreenActivity();
                        overridePendingTransition(0, 0);
                    }
                    else{}
                    currentActivity = 1;
                    break;
                case R.id.nav_search:
                    if (currentActivity != 2) {
                        startActivity(new Intent(this, SearchingActivity.class));
                        overridePendingTransition(0, 0);
                    }
                    else{}
                    currentActivity = 2;
                    break;
                case R.id.nav_appointment:
                    if (currentActivity != 3) {
                        startActivity(new Intent(this, AppointmentConfirmationActivity.class));
                        overridePendingTransition(0, 0);
                    }
                    else{}
                    currentActivity = 3;
                    break;
                case R.id.nav_messaging:
                    if (currentActivity != 4) {
                        startActivity(new Intent(this, MessagingActivity.class));
                        overridePendingTransition(0, 0);
                    }
                    else{}
                    currentActivity = 4;
                    break;
                case R.id.nav_profile:
                    if (currentActivity != 5) {
                        startActivity(new Intent(this, ProfileActivity.class));
                        overridePendingTransition(0, 0);
                    }
                    else{}
                    currentActivity = 5;
                    break;
                case R.id.nav_settings:
                    if (currentActivity != 6) {
                        startActivity(new Intent(this, SettingsActivity.class));
                        overridePendingTransition(0, 0);
                    }
                    else{}
                    currentActivity = 6;
                    break;
            }
            return false;
        }


    protected void allocateActivityTitle(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }
    private void goToHomeScreenActivity () {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        dReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = fUser.getUid();
        dReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null && user.getUserType() == UserType.Client) {
                    goToClientHomeScreenActivity();
                } else if (user != null && user.getUserType() == UserType.Agent) {
                    goToAgentHomeScreenActivity();
                } else if (user != null && user.getUserType() == UserType.Contractor) {
                    goToContractorHomeScreenActivity();
                } else {
                    Snackbar.make(findViewById(R.id.loginEnter), "Something went wrong", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void goToClientHomeScreenActivity() {
        Intent intent = new Intent(this, ClientHomeScreenActivity.class);
        startActivity(intent);
    }
    public void goToContractorHomeScreenActivity() {
        Intent intent = new Intent(this, ContractorHomeScreenActivity.class);
        startActivity(intent);
    }
    public void goToAgentHomeScreenActivity() {
        Intent intent = new Intent(this, AgentHomeScreenActivity.class);
        startActivity(intent);
    }

    public static void setCurrentActivity(int xCurrentActivity) {
        currentActivity = xCurrentActivity;
    }

/*

*/

}
