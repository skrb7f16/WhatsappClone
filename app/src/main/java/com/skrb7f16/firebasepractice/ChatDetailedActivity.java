package com.skrb7f16.firebasepractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skrb7f16.firebasepractice.Adapters.ChatAdapter;
import com.skrb7f16.firebasepractice.Models.Messages;
import com.skrb7f16.firebasepractice.Models.Users;
import com.skrb7f16.firebasepractice.databinding.ActivityChatDetailedBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailedActivity extends AppCompatActivity {

    ActivityChatDetailedBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance("https://fir-practice-a3756-default-rtdb.firebaseio.com/");
        auth=FirebaseAuth.getInstance();
        final String senderId=auth.getUid();
        final String recieverId=getIntent().getStringExtra("userId");
        final String username=getIntent().getStringExtra("username");
        final String profilePic=getIntent().getStringExtra("profilePic");
        binding.Username.setText(username);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_user).into(binding.dp);
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user=snapshot.getValue(Users.class);
                about=user.getAbout();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChatDetailedActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        binding.dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChatDetailedActivity.this,UserProfileActivity.class);
                intent.putExtra("userId",recieverId);
                intent.putExtra("username",username);
                intent.putExtra("profilePic",profilePic);
                intent.putExtra("about",about);
                startActivity(intent);

            }
        });
        final ArrayList<Messages> messageModels=new ArrayList<>();

        final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this);
        binding.chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        binding.chatRecyclerView.setLayoutManager(layoutManager);





        final String senderRoom=senderId+recieverId;
        final String recieverRoom=recieverId+senderId;


        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren() ){
                    Messages model=dataSnapshot.getValue(Messages.class);
                    messageModels.add(model);

                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=binding.etMessage.getText().toString();
                final Messages model=new Messages(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");
                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("chats").child(recieverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });
            }
        });

    }
}