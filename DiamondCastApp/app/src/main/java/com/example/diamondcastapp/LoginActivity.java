package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goToHomeScreenActivity (View view) {
        //if(loggingIn()) {
            Intent intent = new Intent(this, ClientHomeScreenActivity.class);
            startActivity(intent);
        //}
    }

    public void goToForgotPasswordActivity (View view) {
        /*Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);*/
    }

    public void goToCreateAccount1Activity (View view) {
        Intent intent = new Intent(this, CreateAccount1Activity.class);
        startActivity(intent);
    }

    private boolean loggingIn() {
        boolean loggedIn = false;
        EditText usernameInput = findViewById(R.id.loginUsernameInput);
        EditText passwordInput = findViewById(R.id.loginPasswordInput);
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        //Try to find username and password from server here

        if (false) {
            Snackbar.make(findViewById(R.id.loginUsernameInput), "Username not found", Snackbar.LENGTH_SHORT).show();
        } else {
            if (false) {
                Snackbar.make(findViewById(R.id.loginPasswordInput), "Incorrect password", Snackbar.LENGTH_SHORT).show();
            } else {
                loggedIn = true;
            }
        }
        return loggedIn;
    }
}