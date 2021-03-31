package com.skrb7f16.firebasepractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skrb7f16.firebasepractice.Adapters.ChatAdapter;
import com.skrb7f16.firebasepractice.Adapters.GroupAdapter;
import com.skrb7f16.firebasepractice.Models.GroupMessageModel;
import com.skrb7f16.firebasepractice.Models.Messages;
import com.skrb7f16.firebasepractice.Models.Users;
import com.skrb7f16.firebasepractice.databinding.ActivityGroupChatBinding;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String tempName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        binding= ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance("https://fir-practice-a3756-default-rtdb.firebaseio.com/");
        auth=FirebaseAuth.getInstance();

        final ArrayList<GroupMessageModel> messageModels=new ArrayList<>();

        final GroupAdapter chatAdapter=new GroupAdapter(messageModels,this);
        binding.chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        binding.chatRecyclerView.setLayoutManager(layoutManager);
        
        tempName=getIntent().getStringExtra("username");


        database.getReference().child("GroupChat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    GroupMessageModel model=dataSnapshot.getValue(GroupMessageModel.class);
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
                GroupMessageModel model=new GroupMessageModel();
                model.setMessage(binding.etMessage.getText().toString());
                model.setSenderName(tempName);
                model.setuId(auth.getUid());
                model.setTimestamp(new Date().getTime());
                database.getReference().child("GroupChat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        binding.etMessage.setText("");
                    }
                });
            }
        });


    }
}