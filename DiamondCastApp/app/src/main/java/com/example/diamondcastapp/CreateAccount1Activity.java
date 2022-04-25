package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount1Activity extends AppCompatActivity {

    private User newUser;
    private String password;
    private CreateAccount createAccountMethods = new CreateAccount();

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

        EditText firstNameInput = findViewById(R.id.createAccount1FirstNameInput);
        EditText lastNameInput = findViewById(R.id.createAccount1LastNameInput);
        EditText emailInput = findViewById(R.id.createAccount1EmailInput);
        EditText usernameInput = findViewById(R.id.createAccount1UsernameInput);
        EditText passwordInput = findViewById(R.id.createAccount1PasswordInput);
        EditText confirmPasswordInput = findViewById(R.id.createAccount1ComfirmPasswordInput);

        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if(firstName.isEmpty()) {
            Toast.makeText(CreateAccount1Activity.this, "Enter your first name", Toast.LENGTH_SHORT).show();
        } else if(lastName.isEmpty()) {
            Toast.makeText(CreateAccount1Activity.this, "Enter your last name", Toast.LENGTH_SHORT).show();
        } else if(email.isEmpty()) {
            Toast.makeText(CreateAccount1Activity.this, "Enter an email", Toast.LENGTH_SHORT).show();
        } else if(!createAccountMethods.checkValidEmail(email)){
            Toast.makeText(CreateAccount1Activity.this, "Email is invalid", Toast.LENGTH_SHORT).show();
        } else if(username.isEmpty()) {
            Toast.makeText(CreateAccount1Activity.this, "Enter a username", Toast.LENGTH_SHORT).show();
        } else if(password.isEmpty()) {
            Toast.makeText(CreateAccount1Activity.this, "Enter a password", Toast.LENGTH_SHORT).show();
        } else if(confirmPassword.isEmpty()) {
            Toast.makeText(CreateAccount1Activity.this, "Confirm your password.", Toast.LENGTH_SHORT).show();
        } else if(!createAccountMethods.checkPasswordRequirementsMet(password)) {
            Toast.makeText(CreateAccount1Activity.this, "Password must be between ", Toast.LENGTH_SHORT).show();
        } else if(!createAccountMethods.checkPasswordIsEqualToConfirmation(password, confirmPassword)) {
            Toast.makeText(CreateAccount1Activity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
        } else {
            createdAccount = true;
        }

        newUser = new User(firstName, lastName, email, username, UserType.Client);
        return createdAccount;
    }
}