package com.example.whatsapp2.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp2.MainActivity;
import com.example.whatsapp2.Models.Users;
import com.example.whatsapp2.R;
import com.example.whatsapp2.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivitySettingsBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

       firebaseStorage = FirebaseStorage.getInstance();
       mAuth = FirebaseAuth.getInstance();
       database = FirebaseDatabase.getInstance();

       binding.appBackArrow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
               startActivity(intent);
           }
       });

       database.getReference().child("User").child(Objects.requireNonNull(mAuth.getUid()))
                       .addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               try {
                                   Users users = snapshot.getValue(Users.class);
                                   Picasso.get().load(users.getProfilePic())
                                           .placeholder(R.drawable.avatar)
                                           .into(binding.appProfilePic);

                                   binding.appUserName.setText(users.getUserName());
                                   binding.appUserStatus.setText(users.getStatus());
                               } catch (Exception ex){
                                   Toast.makeText(SettingsActivity.this,"Error faced "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });

       binding.appPlus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent();
               intent.setAction(Intent.ACTION_GET_CONTENT);
               intent.setType("image/*");
               startActivityForResult(intent,25);
           }
       });

       binding.appSaveBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!binding.appUserName.getText().toString().equals("") && !binding.appUserStatus.getText().toString().equals("")){
                   String name = binding.appUserName.getText().toString();
                   String about = binding.appUserStatus.getText().toString();

                   HashMap<String,Object> userData = new HashMap<>();
                   userData.put("userName",name);
                   userData.put("status",about);

                   database.getReference().child("User")
                           .child(mAuth.getUid())
                           .updateChildren(userData);

                   Toast.makeText(SettingsActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();

                   binding.appUserName.setText("");
                   binding.appUserStatus.setText("");
               }else {
                   Toast.makeText(SettingsActivity.this, "It can't be Empty", Toast.LENGTH_SHORT).show();
               }
           }
       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (data.getData() != null){
            Uri imageUri = data.getData();
            binding.appProfilePic.setImageURI(imageUri);

            final StorageReference reference = firebaseStorage.getReference().child("profile_pic")
                    .child(Objects.requireNonNull(mAuth.getUid()));

            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("User")
                                    .child(mAuth.getUid())
                                    .child("profilePic").setValue(imageUri.toString());
                        }
                    });
                }
            });
        }
    }
}