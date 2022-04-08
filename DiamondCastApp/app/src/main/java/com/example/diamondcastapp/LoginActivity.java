package com.example.diamondcastapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private String userID, mail;
    private Button verifyButton,changePass;
    private TextView verifyMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();

        changePass = findViewById(R.id.loginForgotPassword);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start
                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());

                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter the email associated with your account: ");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                    mail = resetMail.getText().toString();
                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener((OnSuccessListener) (aVoid) ->{
                        Toast.makeText(LoginActivity.this, "Reset Link Sent. Check your email.", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener((e)-> {
                        Toast.makeText(LoginActivity.this, "Error Link was not sent to email" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                });
                passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                    //CLOSE
                });
                passwordResetDialog.create().show();
            }
        });
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
                        final FirebaseUser user = fAuth.getCurrentUser();
                        verifyButton = findViewById(R.id.verifyButton);
                        verifyMsg = findViewById(R.id.verifyMsg);
                        if(!user.isEmailVerified()){
                            verifyMsg.setVisibility(View.VISIBLE);
                            verifyButton.setVisibility(View.VISIBLE);
                            verifyButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    verifyMsg.setVisibility(View.GONE);
                                    verifyButton.setVisibility(View.GONE);
                                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>(){
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(LoginActivity.this, "Verification Email has been Sent.", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("tag", "onFailure: Email not sent" +e.getMessage());
                                        }
                                    });
                                }
                            });
                        }
                        if(user.isEmailVerified()) {
                            goToHomeScreenActivity();
                        }
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
               // } else if(user!=null && user.getUserType()==UserType.Client) {
                 //   goToClientNewUserActivity();
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

    /*
    private void goToClientNewUserActivity() {
        Intent intent = new Intent(this, ClientNUCActivity.class);
        startActivity(intent);
    }
    */

}