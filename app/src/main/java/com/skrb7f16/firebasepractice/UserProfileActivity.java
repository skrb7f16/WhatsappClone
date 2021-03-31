package com.skrb7f16.firebasepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.skrb7f16.firebasepractice.databinding.ActivityUserProfileBinding;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    ActivityUserProfileBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String url;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        binding=ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        final String recieverId=getIntent().getStringExtra("userId");
        final String username=getIntent().getStringExtra("username");
        final String profilePic=getIntent().getStringExtra("profilePic");
        final String about=getIntent().getStringExtra("about");
        binding.userUsername.setText(username);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_user).into(binding.userProfilePic);
        binding.userAboutText.setText(about);
    }
}