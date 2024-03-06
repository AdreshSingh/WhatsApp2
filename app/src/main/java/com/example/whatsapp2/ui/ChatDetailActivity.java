package com.example.whatsapp2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.whatsapp2.Adapters.ChatAdapter;
import com.example.whatsapp2.MainActivity;
import com.example.whatsapp2.Models.MessageModel;
import com.example.whatsapp2.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    com.example.whatsapp2.databinding.ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    final static  String DATABASE_CHAT = "chats";
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

        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels,this,receiveId);

        binding.appChatRecyclerView.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.appChatRecyclerView.setLayoutManager(linearLayoutManager);

        final String senderRoom = userId  + receiveId;
        final String receiverRoom = receiveId + userId;

        database.getReference().child(DATABASE_CHAT)
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                             messageModels.clear();
                             for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                 MessageModel model = dataSnapshot.getValue(MessageModel.class);
                                 messageModels.add(model);
                             }
                             chatAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.appSendButton.setOnClickListener(view ->{
            String sendMsg = binding.appMessage.getText().toString();
            final MessageModel model = new MessageModel(userId,sendMsg);
            model.setTimeStamp(new Date().getTime());
            binding.appMessage.setText("");

            database.getReference().child(DATABASE_CHAT)
                    .child(senderRoom)
                    .push()
                    .setValue(model)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            database.getReference().child(DATABASE_CHAT)
                                    .child(receiverRoom)
                                    .push()
                                    .setValue(model)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    });
                        }
                    });

        });
    }
}