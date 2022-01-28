package com.example.diamondcastapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;

public class CreateAccount2Activity extends AppCompatActivity {

    private Spinner accountTypeSpinner;
    private static User newUser;
    private static String password;
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

    private void goToLoginActivity () {
        Snackbar.make(findViewById(R.id.createAccount2CreateAccount), "User has been registered", Snackbar.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
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
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser fUser = fAuth.getCurrentUser();
                            fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>(){
                              @Override
                              public void onSuccess(Void aVoid) {
                                  Toast.makeText(CreateAccount2Activity.this, "Verification Email has been Sent.", Toast.LENGTH_SHORT).show();
                              }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent" +e.getMessage());
                                }
                            });

                            String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            newUser.setId(Uid);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Uid).setValue(newUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        if(newUser.getUserType() == UserType.Contractor) {
                                            Contractor newContractor = new Contractor(newUser);
                                            FirebaseDatabase.getInstance().getReference("Contractors")
                                                    .child(Uid).setValue(newContractor);
                                        }
                                        else if(newUser.getUserType() == UserType.Agent) {
                                            Agent newAgent = new Agent(newUser);
                                            FirebaseDatabase.getInstance().getReference("Agents")
                                                    .child(Uid).setValue(newAgent);
                                        }


                                        goToLoginActivity();
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

    public static void setNewUserAndPasswordCreateAccount2(User xNewUser, String xPassword) {
        newUser = xNewUser;
        password = xPassword;
    }

}