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
    private User newUser;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account1);
    }

    public void goToCreateAccount2Activity (View view) {
        if (creatingAccount()) {
            CreateAccount2Activity.setNewUserAndPasswordCreateAccount2(newUser, password);
            Intent intent = new Intent(this, CreateAccount2Activity.class);
            startActivity(intent);
        }
    }

    public void goToLoginActivity (View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean creatingAccount() {
        boolean createdAccount = false;
        int passwordMin = 6;
        int passwordMax = 30;

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
        password = passwordInput.getText().toString();
        String comfirmPassword = comfirmPasswordInput.getText().toString();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(firstName.isEmpty()) {
            Snackbar.make(findViewById(R.id.createAccount1FirstNameInput), "Enter your first name", Snackbar.LENGTH_SHORT).show();
        } else if(lastName.isEmpty()) {
            Snackbar.make(findViewById(R.id.createAccount1LastNameInput), "Enter your last name", Snackbar.LENGTH_SHORT).show();
        } else if(email.isEmpty()) {
            Snackbar.make(findViewById(R.id.createAccount1EmailInput), "Enter an email", Snackbar.LENGTH_SHORT).show();
        } else if(!matcher.matches()){
            Snackbar.make(findViewById(R.id.createAccount1EmailInput), "Email is invalid", Snackbar.LENGTH_SHORT).show();
        } else if(false) {//TODO check if email is already in use
            Snackbar.make(findViewById(R.id.createAccount1EmailInput), "Email already in use", Snackbar.LENGTH_SHORT).show();
        } else if(username.isEmpty()) {
            Snackbar.make(findViewById(R.id.createAccount1UsernameInput), "Enter a username", Snackbar.LENGTH_SHORT).show();
        } else if(false) {//TODO check if username is already in use
            Snackbar.make(findViewById(R.id.createAccount1UsernameInput), "Username already in use", Snackbar.LENGTH_SHORT).show();
        } else if(password.isEmpty()) {
            Snackbar.make(findViewById(R.id.createAccount1PasswordInput), "Enter a password", Snackbar.LENGTH_SHORT).show();
        } else if(password.length()<passwordMin){
            Snackbar.make(findViewById(R.id.createAccount1PasswordInput), "Password is too short ("+passwordMin+" characters min)", Snackbar.LENGTH_SHORT).show();
        } else if(password.length()>passwordMax){
            Snackbar.make(findViewById(R.id.createAccount1PasswordInput), "Password is too long ("+passwordMax+" characters max)", Snackbar.LENGTH_SHORT).show();
        } else if(comfirmPassword.isEmpty()) {
            Snackbar.make(findViewById(R.id.createAccount1ComfirmPasswordInput), "Confirm your password", Snackbar.LENGTH_SHORT).show();
        } else if(!comfirmPassword.equals(password)) {
            Snackbar.make(findViewById(R.id.createAccount1ComfirmPasswordInput), "Passwords do not match", Snackbar.LENGTH_SHORT).show();
        } else {
            createdAccount = true;
        }

        newUser = new User(firstName, lastName, email, username, UserType.Client);
        return createdAccount;
    }


}