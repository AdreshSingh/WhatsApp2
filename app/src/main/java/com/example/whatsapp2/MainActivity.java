package com.example.whatsapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp2.Adapters.FragmentsAdaptor;
import com.example.whatsapp2.Models.Users;
import com.example.whatsapp2.databinding.ActivityMainBinding;
import com.example.whatsapp2.ui.GroupChatActivity;
import com.example.whatsapp2.ui.SettingsActivity;
import com.example.whatsapp2.ui.SignInActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    // Accessing features of Firebase authentication
    FirebaseAuth mAuth;
    // Accessing firebase database
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.appViewPager.setAdapter(new FragmentsAdaptor(getSupportFragmentManager()));
        binding.appTabLayOut.setupWithViewPager(binding.appViewPager);

        // creating user SignIn timeStamp
        HashMap<String,Object> userDt = new HashMap<>();
        userDt.put("timeStamp",new Date().getTime());


        firebaseDatabase.getReference().child("User")
                .child(Objects.requireNonNull(mAuth.getUid()))
                .updateChildren(userDt)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

        setSupportActionBar(binding.topAppBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int option = item.getItemId();
        if (option == R.id.appSettings) {
            createToast("Clicked Settings");
            launchActivity(MainActivity.this, SettingsActivity.class);
        } else if (option == R.id.appGroupChat) {
            launchActivity(MainActivity.this, GroupChatActivity.class);
            createToast("Group chat clicked");
        } else if (option== R.id.appLogOut) {
            createToast("Logout clicked");
            mAuth.signOut();
            launchActivity(MainActivity.this, SignInActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    // for creating debugs log using Toast procedure
    public void  createToast(String data){
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    // for launching new Activity from current activity
    private void launchActivity(Context currentActivity, Class<?> nextActivity){
        Intent intent = new Intent(currentActivity,nextActivity);
        startActivity(intent);
    }
}