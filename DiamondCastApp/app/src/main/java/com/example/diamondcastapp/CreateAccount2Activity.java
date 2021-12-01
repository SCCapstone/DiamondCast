package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount2Activity extends AppCompatActivity {

    private Spinner accountTypeSpinner;
    private static registerUser newUser;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account2);

        accountTypeSpinner = (Spinner) findViewById(R.id.createAccount2AccountType);
        ArrayAdapter<String> accountTypeAdapter = new ArrayAdapter<String>(CreateAccount2Activity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.accountTypes));
        accountTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(accountTypeAdapter);
    }

    public void goToCreateAccount1Activity (View view) {

        Intent intent = new Intent(this, CreateAccount1Activity.class);
        startActivity(intent);
    }

    private void goToHomeScreenActivity () {
            Class nextClass = ClientHomeScreenActivity.class;
            UserType type = newUser.getUserType();
            if (type == UserType.Client) {
                nextClass = ClientHomeScreenActivity.class;
            } else if (type == UserType.Agent) {
                nextClass = AgentHomeScreenActivity.class;
            } else if (type == UserType.Contractor) {
                nextClass = ContractorHomeScreenActivity.class;
            } else {
                //TODO other potential user types
            }
            Intent intent = new Intent(this, nextClass);
            startActivity(intent);
    }

    public void createAccount (View view) {
        int position = accountTypeSpinner.getSelectedItemPosition();
        switch (position) {
            case 0:
                newUser.setUserType(UserType.Client);
                break;
            case 1:
                newUser.setUserType(UserType.Agent);
                break;
            case 2:
                newUser.setUserType(UserType.Contractor);
                break;
        }

        fAuth = FirebaseAuth.getInstance();
        String email = newUser.getEmail();
        String password = newUser.passwordForCreatingAccount();
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(newUser.getUserType().toString())
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        goToHomeScreenActivity();
                                        Snackbar.make(findViewById(R.id.createAccount2CreateAccount), "User has been registered", Snackbar.LENGTH_SHORT).show();
                                    } else{
                                        Snackbar.make(findViewById(R.id.createAccount2CreateAccount), "Failed to register try again(2)", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else{
                            Snackbar.make(findViewById(R.id.createAccount2CreateAccount), "Failed to register try again(1)", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void setNewUserCreateAccount2(registerUser xNewUser) {
        newUser = xNewUser;
    }

}