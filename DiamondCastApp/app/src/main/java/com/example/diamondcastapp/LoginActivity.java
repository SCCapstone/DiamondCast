package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goToHomeScreenActivity (View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    public void goToForgotPasswordActivity (View view) {
        /*Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);*/
    }

    public void goToCreateAccount1Activity (View view) {
        Intent intent = new Intent(this, CreateAccount1Activity.class);
        startActivity(intent);
    }
}