package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
    }
    public void goToSideBarActivity (View view) {
       //need side bar activity Intent intent = new Intent(this, SideBarActivity.class);
        //startActivity(intent);
    }
    private void searchInbox(View view) {
        //TODO
    }
    private void makeMessage(View view) {
        //TODO
    }
    private void selectMessage(View view) {
        //TODO
    }
}