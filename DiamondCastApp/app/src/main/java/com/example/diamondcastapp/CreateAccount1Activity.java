package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount1Activity extends AppCompatActivity {
    private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account1);
    }

    public void goToCreateAccount2Activity (View view) {
        //if (creatingAccount()) {
            Intent intent = new Intent(this, CreateAccount2Activity.class);
            startActivity(intent);
        //}
    }

    public void goToLoginActivity (View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public boolean creatingAccount() {
        boolean createdAccount = false;

        EditText firstNameInput = findViewById(R.id.createAccount1FirstNameInput);
        EditText lastNameInput = findViewById(R.id.createAccount1LastNameInput);
        EditText emailInput = findViewById(R.id.createAccount1EmailInput);
        EditText usernameInput = findViewById(R.id.createAccount1UsernameInput);
        EditText passwordInput = findViewById(R.id.createAccount1PasswordInput);
        EditText comfirmPasswordInput = findViewById(R.id.createAccount1ComfirmPasswordInput);

        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String comfirmPassword = comfirmPasswordInput.getText().toString();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        //Try to find email and username from server here

        if(firstName == "") {
            Snackbar.make(findViewById(R.id.createAccount1FirstNameInput), "Enter your first name", Snackbar.LENGTH_SHORT).show();
        } else if(lastName == "") {
            Snackbar.make(findViewById(R.id.createAccount1LastNameInput), "Enter your last name", Snackbar.LENGTH_SHORT).show();
        } else if(email == "") {
            Snackbar.make(findViewById(R.id.createAccount1EmailInput), "Enter an email", Snackbar.LENGTH_SHORT).show();
        } else if(!matcher.matches()){
            Snackbar.make(findViewById(R.id.createAccount1EmailInput), "Email is invalid", Snackbar.LENGTH_SHORT).show();
        } else if(false) {
            Snackbar.make(findViewById(R.id.createAccount1EmailInput), "Email already in use", Snackbar.LENGTH_SHORT).show();
        } else if(username == "") {
            Snackbar.make(findViewById(R.id.createAccount1UsernameInput), "Enter a username", Snackbar.LENGTH_SHORT).show();
        } else if(false) {
            Snackbar.make(findViewById(R.id.createAccount1UsernameInput), "Username already in use", Snackbar.LENGTH_SHORT).show();
        } else if(password == "") {
            Snackbar.make(findViewById(R.id.createAccount1PasswordInput), "Enter a password", Snackbar.LENGTH_SHORT).show();
        } else if(comfirmPassword == "") {
            Snackbar.make(findViewById(R.id.createAccount1ComfirmPasswordInput), "Confirm your password", Snackbar.LENGTH_SHORT).show();
        } else if(comfirmPassword != password) {
            Snackbar.make(findViewById(R.id.createAccount1ComfirmPasswordInput), "Passwords do not match", Snackbar.LENGTH_SHORT).show();
        } else {
            createdAccount = true;
        }
        return createdAccount;
    }

}