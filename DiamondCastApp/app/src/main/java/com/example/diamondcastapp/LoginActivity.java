package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    private DatabaseReference dReference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loggingIn(View view) {
        EditText emailInput = findViewById(R.id.loginEmailInput);
        EditText passwordInput = findViewById(R.id.loginPasswordInput);
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        fAuth = FirebaseAuth.getInstance();

        if(email.isEmpty()) {
            Snackbar.make(findViewById(R.id.loginEmailInput), "Enter your email", Snackbar.LENGTH_SHORT).show();
        } else if(password.isEmpty()) {
            Snackbar.make(findViewById(R.id.loginPasswordInput), "Enter your password", Snackbar.LENGTH_SHORT).show();
        } else {
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        goToHomeScreenActivity();
                    } else {
                        Snackbar.make(findViewById(R.id.loginPasswordInput), "Login failed, check your email and password", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void goToCreateAccount1Activity (View view) {
        Intent intent = new Intent(this, CreateAccount1Activity.class);
        startActivity(intent);
    }

    public void goToForgotPasswordActivity (View view) {
        /*Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);*/
    }

    private void goToHomeScreenActivity () {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        dReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = fUser.getUid();
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
                    Snackbar.make(findViewById(R.id.loginEnter), "Something went wrong", Snackbar.LENGTH_SHORT).show();
                }
         }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
               Snackbar.make(findViewById(R.id.loginEnter), "An error has occurred: "+error, Snackbar.LENGTH_SHORT).show();
          }
        });
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




}