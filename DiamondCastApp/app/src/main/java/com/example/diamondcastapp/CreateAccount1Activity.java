package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount1Activity extends AppCompatActivity {
    private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private User newUser;
    private String password;
    private static final int PASSWORD_MIN = 6;
    private static final int PASSWORD_MAX = 30;
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
        int passwordMin = 6;
        int passwordMax = 30;

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
        } else if(confirmPassword.isEmpty()) {
            Snackbar.make(findViewById(R.id.createAccount1ComfirmPasswordInput), "Confirm your password.", Snackbar.LENGTH_SHORT).show();
        } else if(!createAccountMethods.checkPasswordRequirementsMet(password)) {
            Snackbar.make(findViewById(R.id.createAccount1PasswordInput), "Password must be between " + PASSWORD_MIN + " and " + PASSWORD_MAX + "characters.", Snackbar.LENGTH_SHORT).show();
        } else if(!createAccountMethods.checkPasswordIsEqualToConfirmation(password, confirmPassword)) {
            Snackbar.make(findViewById(R.id.createAccount1ComfirmPasswordInput), "Confirm your password.", Snackbar.LENGTH_SHORT).show();
        }
        else {
            createdAccount = true;
        }

        newUser = new User(firstName, lastName, email, username, UserType.Client);
        return createdAccount;
    }
    //Determines if password requirements are met
    // Password Requirements
    //   - length is (6-30) characters
    //   - more to come? (needs at least 1 number 1 uppercase)
    // **an empty password will fail check**
    public boolean checkPasswordRequirementsMet(String password) {
        if (password.isEmpty()) {
            Snackbar.make(findViewById(R.id.createAccount1PasswordInput), "Enter a password", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if(password.length() < PASSWORD_MIN || password.length() > PASSWORD_MAX) {
            Snackbar.make(findViewById(R.id.createAccount1PasswordInput), "Password must be between " + PASSWORD_MIN + " and " + PASSWORD_MAX + "characters.", Snackbar.LENGTH_SHORT).show();
            return false;
       }
        else {
            return true;
        }
    }
    //returns true if password and confirm password are equal
    public boolean checkPasswordIsEqualToConfirmation(String password, String confirmPassword) {
            if(confirmPassword.isEmpty()) {
                Snackbar.make(findViewById(R.id.createAccount1ComfirmPasswordInput), "Confirm your password.", Snackbar.LENGTH_SHORT).show();
                return false;
            }
            else if(!(password.equals(confirmPassword))) {
                Snackbar.make(findViewById(R.id.createAccount1ComfirmPasswordInput), "Passwords do not match.", Snackbar.LENGTH_SHORT).show();
                return false;
            }
            else { return true; }
    }


}