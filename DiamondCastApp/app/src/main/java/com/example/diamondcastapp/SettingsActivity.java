package com.example.diamondcastapp;

import static android.content.ContentValues.TAG;
import static android.telephony.PhoneNumberUtils.isISODigit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends NavigationDrawerActivity {
    Button changePass, changePhone;
    FirebaseAuth fAuth;
    String newPassword, newPhone, userTypeDb;
    boolean isoIsTrue = true;
    FirebaseUser currentUser;
    ActivitySettingsBinding activitySettingsBinding;
    /* L/D Mode
    SwitchCompat switchCompat;
    SharedPreferences sharedPreferences = null; */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(activitySettingsBinding.getRoot());
        allocateActivityTitle("Settings");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
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
        */

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

        //Change Phone Number
        //check users type first
        changePhone = findViewById(R.id.changePhone);
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Users");
        DatabaseReference current_userRef = usersRef.child(currentUser.getUid());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            //get name and user type from database
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO? Contractors have no phone number, this might give an error.
                userTypeDb = dataSnapshot.child("userType").getValue(String.class);
                //name.setText(firstNameStr + " " + lastNameStr);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        current_userRef.addListenerForSingleValueEvent(eventListener);

        //set onclick listener for "change phone num" button
        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText changePhone = new EditText(v.getContext());
                final AlertDialog.Builder PhoneResetDialog = new AlertDialog.Builder(v.getContext());
                //New number prompt
                PhoneResetDialog.setTitle("Set a new phone number?");
                PhoneResetDialog.setMessage("Enter new number below (NO dashes '-'): ");
                PhoneResetDialog.setView(changePhone);
                PhoneResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                        newPhone = changePhone.getText().toString();
                        //public static boolean isISODigit (char c), checks if 0-9
                        for (int i = 0; i<newPhone.length(); i++){
                            if(isISODigit(newPhone.charAt(i))){
                            }
                            else{
                                isoIsTrue = false;
                                break;

                            }
                        }
                        //Log.d(TAG, "onClick: " +newPhone);
                        //make sure phone number is 10 digits
                        if (newPhone.length() == 10 && isoIsTrue == true) {
                            FirebaseDatabase.getInstance().getReference(userTypeDb+"s").child(currentUser.getUid()).child("phone").setValue(PhoneNumberUtils.formatNumber(newPhone))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(SettingsActivity.this, "Changed phone number!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SettingsActivity.this, "Phone number change failed, try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        //If phone number is non-conforming, then send toast to user
                        else if(newPhone.length() < 10 && isoIsTrue == true){
                            Toast.makeText(SettingsActivity.this, "Your phone number was less than 10 digits.", Toast.LENGTH_LONG).show();
                        }
                        else if(newPhone.length() > 10 && isoIsTrue == true){
                            Toast.makeText(SettingsActivity.this, "Your phone number was greater than 10 digits. Don't include country code", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(SettingsActivity.this, "Your phone number must be ONLY numbers (0-9)", Toast.LENGTH_SHORT).show();
                            //reset check if number is ISO
                            isoIsTrue = true;
                        }
                });
                PhoneResetDialog.setNegativeButton("No", (dialog, which) -> {
                    //NO, CLOSE
                });
                PhoneResetDialog.create().show();
            }
        });
    }
}