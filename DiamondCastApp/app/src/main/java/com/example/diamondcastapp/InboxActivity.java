package com.example.diamondcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.diamondcastapp.databinding.ActivityInboxBinding;

public class InboxActivity extends NavigationDrawerActivity {
    ActivityInboxBinding activityInboxBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInboxBinding = ActivityInboxBinding.inflate(getLayoutInflater());
        setContentView(activityInboxBinding.getRoot());
        allocateActivityTitle("Messaging");
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