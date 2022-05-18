package com.example.diamondcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ImageView profileImage, chatSendButton;
    TextView fullName;
    EditText chatText;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    ChatAdapter chatAdapter;
    List<Message> messages;
    RecyclerView chatRecycleView;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.chatToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chatRecycleView = findViewById(R.id.chatRecycleView);
        chatRecycleView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        chatRecycleView.setLayoutManager(linearLayoutManager);

        profileImage = findViewById(R.id.profileImage);
        fullName = findViewById(R.id.fullName);
        chatText = findViewById(R.id.chatText);
        chatSendButton = findViewById(R.id.chatSendButton);

        intent = getIntent();
        final String id = intent.getStringExtra("id");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = chatText.getText().toString();
                if(!message.equals("")){
                    sendMessages(firebaseUser.getUid(), id, message);
                } else {
                    Toast.makeText(ChatActivity.this, "Enter your message",
                            Toast.LENGTH_SHORT).show();
                }
                chatText.setText("");
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                fullName.setText(user.getFirstName()+" "+user.getLastName());

                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference profileRef = storageReference.child("users/"+user.getId()+"/profile.jpg");
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });

                readMessages(firebaseUser.getUid(), id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "An error has occurred: "+error,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Sends messages to the database
    private void sendMessages(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Messages").push().setValue(hashMap);
    }

    //Reads messages from the database that are from and to current user
    private void readMessages(final String currentUserId, final String otherUserId){
        messages = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if(message.getReceiver().equals(currentUserId) && message.getSender()
                            .equals(otherUserId) || message.getReceiver().equals(otherUserId) &&
                            message.getSender().equals(currentUserId)) {
                        messages.add(message);
                    }
                }
                chatAdapter = new ChatAdapter(ChatActivity.this, messages);
                chatRecycleView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "An error has occurred: "+error,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}