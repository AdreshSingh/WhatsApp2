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

import com.example.whatsapp2.databinding.ActivityMainBinding;
import com.example.whatsapp2.ui.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
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
        } else if (option == R.id.appGroupChat) {
            createToast("Group chat clicked");
        } else if (option== R.id.appLogOut) {
            createToast("Logout clicked");
            mAuth.signOut();
            launchActivity(MainActivity.this, SignInActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }
    public void  createToast(String data){
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
    // for launching new Activity from current activity
    private void launchActivity(Context currentActivity, Class<?> nextActivity){
        Intent intent = new Intent(currentActivity,nextActivity);
        startActivity(intent);
    }
}