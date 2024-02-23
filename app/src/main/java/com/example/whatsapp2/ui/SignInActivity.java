package com.example.whatsapp2.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp2.MainActivity;
import com.example.whatsapp2.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait, Validation is in progress");

        binding.appBtnSignIn.setOnClickListener(v -> {
            String userEmail = binding.appTextEmail.getText().toString();
            String userPassword = binding.appTextPassword.getText().toString();

            if (!userEmail.isEmpty() && !userPassword.isEmpty()){
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        launchActivity(SignInActivity.this, MainActivity.class);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }else {
                Toast.makeText(this, "Enter credentials", Toast.LENGTH_SHORT).show();
            }
        });

        binding.appTextClickSignUp.setOnClickListener(view -> launchActivity(SignInActivity.this, SignUpActivity.class));

        if (mAuth.getCurrentUser() != null){
            launchActivity(SignInActivity.this, MainActivity.class);
        }
    }
    // for launching new Activity from current activity
    private void launchActivity(Context currentActivity,Class<?> nextActivity){
        Intent intent = new Intent(currentActivity,nextActivity);
        startActivity(intent);
    }
}