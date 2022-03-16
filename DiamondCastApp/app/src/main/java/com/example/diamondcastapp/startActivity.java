package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class startActivity extends AppCompatActivity {

    private FirebaseUser fUser;
    private DatabaseReference dReference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Autologin
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(fUser != null){
            userID = fUser.getUid();
            dReference = FirebaseDatabase.getInstance().getReference("Users");
            dReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if(user!=null && user.getUserType()==UserType.Client) {
                        goToClientHomeScreenActivity();
                    } else if(user!=null && user.getUserType()==UserType.Agent) {
                        goToAgentHomeScreenActivity();
                    } else if(user!=null && user.getUserType()==UserType.Contractor) {
                        goToContractorHomeScreenActivity();
                    } else {
                        goToLoginActivity();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Snackbar.make(findViewById(R.id.titleText), "An error has occurred: "+error, Snackbar.LENGTH_SHORT).show();
                }
            });
        } else {
            goToLoginActivity();
        }
    }

    private void goToClientHomeScreenActivity() {
        Intent intent = new Intent(this, ClientHomeScreenActivity.class);
        startActivity(intent);
    }

    private void goToAgentHomeScreenActivity() {
        Intent intent = new Intent(this, AgentHomeScreenActivity.class);
        startActivity(intent);
    }

    private void goToContractorHomeScreenActivity() {
        Intent intent = new Intent(this, ContractorHomeScreenActivity.class);
        startActivity(intent);
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}