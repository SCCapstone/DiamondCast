package com.example.diamondcastapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diamondcastapp.databinding.ActivityProfileBinding;
import com.example.diamondcastapp.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends nav_menu_base {
    ActivitySettingsBinding activitySettingsBinding;
    Button changePass;
    FirebaseAuth fAuth;
    String emailNameStr,newPassword;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(activitySettingsBinding.getRoot());
        allocateActivityTitle("Settings");
        changePass = findViewById(R.id.changePassword);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start

                final EditText changePass = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                //emailNameStr = fAuth.getCurrentUser().getEmail();

                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter a new password below (Over 6 characters long): ");
                passwordResetDialog.setView(changePass);

                passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                    newPassword = changePass.getText().toString();
                    currentUser.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(SettingsActivity.this, "Password Reset was Successful!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SettingsActivity.this, "Password Reset Failed, try a password over 6 characters.", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                    //CLOSE
                });
                passwordResetDialog.create().show();
            }
        });
    }
}