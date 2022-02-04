package com.example.diamondcastapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE = 1023;
    TextView name, email, userType;
    FirebaseAuth fAuth;
    FirebaseUser user;
    Button logoutBtn, changeProfileImage;
    String userId, emailNameStr, userTypeStr, nameStr;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.nameOne);
        email = findViewById(R.id.emailOne);
        userType = findViewById(R.id.userTypeOne);
        profileImage = findViewById(R.id.profileImage);
        changeProfileImage = findViewById(R.id.changeProfileImage);

        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        emailNameStr = fAuth.getCurrentUser().getEmail();
        email.setText(emailNameStr);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Users");
        DatabaseReference current_userRef = usersRef.child(uid);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            //get name and user type from database
            public void onDataChange(DataSnapshot dataSnapshot) {

                String firstNameStr = dataSnapshot.child("firstName").getValue(String.class);
                String lastNameStr = dataSnapshot.child("lastName").getValue(String.class);
                String userTypeStr = dataSnapshot.child("userType").getValue(String.class);
                name.setText(firstNameStr+" "+lastNameStr);
                userType.setText(userTypeStr);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        current_userRef.addListenerForSingleValueEvent(eventListener);

        Button logout = (Button) findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginPage();
            }
        });
        //setting profile image
        ActivityResultLauncher<Intent> thisActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri imageUri = data.getData();
                            profileImage.setImageURI(imageUri);
                        }
                    }

                });
        //setting profile image
        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                thisActivityResultLauncher.launch(openGalleryIntent);
            }
        });
    }
    //logout == goes back to login page
    public void goToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

