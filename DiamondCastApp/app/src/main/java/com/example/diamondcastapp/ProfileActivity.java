package com.example.diamondcastapp;

import static android.content.ContentValues.TAG;

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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diamondcastapp.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class  ProfileActivity extends NavigationDrawerActivity {
    private static final int GALLERY_INTENT_CODE = 1023;
    TextView name, email, userType, locationV;
    FirebaseAuth fAuth;
    FirebaseUser user;
    Button logout, settingsBtn, changeProfileImage;
    String userId, emailNameStr, userTypeStr, userTypeDb, lastNameStr, firstNameStr, locationStr;
    ImageView profileImage;
    StorageReference storageReference;
    ActivityProfileBinding activityProfileBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        allocateActivityTitle("Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        name = findViewById(R.id.nameOne);
        email = findViewById(R.id.emailOne);
        userType = findViewById(R.id.userTypeOne);
        profileImage = findViewById(R.id.profileImage);
        changeProfileImage = findViewById(R.id.changeProfileImage);
        locationV = findViewById(R.id.locationText1);

        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        //Get users profile image from Firebase Storage
        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

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
                firstNameStr = dataSnapshot.child("firstName").getValue(String.class);
                lastNameStr = dataSnapshot.child("lastName").getValue(String.class);
                userTypeStr = dataSnapshot.child("userType").getValue(String.class);
                name.setText(firstNameStr + " " + lastNameStr);
                userType.setText(userTypeStr);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        current_userRef.addListenerForSingleValueEvent(eventListener);
        //Log.d(TAG, "onCreate4: "+userType+" : " +userTypeStr);

        //TODO: userType showing as null can only pass client path
        DatabaseReference root2Ref = FirebaseDatabase.getInstance().getReference("Clients").child(uid);
        //DatabaseReference user2Ref = rootRef.child(userTypeStr+"s");
        //DatabaseReference current2_userRef = root2Ref.child(uid);
       /* ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            //get location
            public void onDataChange(DataSnapshot dataSnapshot) {
                locationStr = dataSnapshot.child("location").getValue(String.class);
                if(locationStr.equals("default")){
                    locationV.setText("None");
                }else {
                    locationV.setText(locationStr);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        root2Ref.addListenerForSingleValueEvent(eventListener2); */


        //LOGOUT BUTTON
        logout = (Button) findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                goToLoginPage();
            }
        });
        //GO TO USER SETTINGS
        settingsBtn = (Button) findViewById(R.id.userSettingBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserSettings();
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
                            //profileImage.setImageURI(imageUri);
                            uploadImageToFirebase(imageUri);
                        }
                    }

                    //image to firebase storage
                    private void uploadImageToFirebase(Uri imageUri) {
                        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
                        Toast.makeText(ProfileActivity.this, "Uploading Image...", Toast.LENGTH_SHORT).show();
                        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(ProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Picasso.get().load(uri).into(profileImage);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "Failed Image Upload", Toast.LENGTH_SHORT).show();
                            }
                        });
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

    //go to settings page
    public void goToUserSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //logout == goes back to login page
    public void goToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

