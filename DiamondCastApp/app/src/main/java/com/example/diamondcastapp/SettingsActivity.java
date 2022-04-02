package com.example.diamondcastapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.diamondcastapp.databinding.ActivitySearchingBinding;
import com.example.diamondcastapp.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends NavigationDrawerActivity {
    Button changePass;
    FirebaseAuth fAuth;
    String newPassword;
    FirebaseUser currentUser;
    SwitchCompat switchCompat;
    SharedPreferences sharedPreferences = null;
    ActivitySettingsBinding activitySettingsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(activitySettingsBinding.getRoot());
        allocateActivityTitle("Settings");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Light Mode / Dark Mode
        switchCompat = findViewById(R.id.darkModeSwitch);
        sharedPreferences = getSharedPreferences("night", 0);
        Boolean booleanVal = sharedPreferences.getBoolean("night_mode",false);
        if(booleanVal){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switchCompat.setChecked(true);
        }
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                    switchCompat.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", true);
                    editor.commit();
                }else{
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_NO));
                    switchCompat.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", false);
                    editor.commit();
                }
            }
        });

        //Password Changer

        changePass = findViewById(R.id.changePassword);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText changePass = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                //Reset password prompt
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