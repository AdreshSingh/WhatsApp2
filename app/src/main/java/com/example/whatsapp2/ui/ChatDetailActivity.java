package com.example.whatsapp2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.whatsapp2.MainActivity;
import com.example.whatsapp2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ChatDetailActivity extends AppCompatActivity {

    com.example.whatsapp2.databinding.ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.whatsapp2.databinding.ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String userId = auth.getUid();
        String receiveId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");

        binding.appUserName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar1).into(binding.appProfilePic);

        binding.appBackArrow.setOnClickListener(view ->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}