package com.example.whatsapp2.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp2.MainActivity;
import com.example.whatsapp2.Models.Users;
import com.example.whatsapp2.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    public static final String DATABASE_OBJECT = "User";
    private FirebaseAuth mAuth;
    ActivitySignUpBinding binding;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account");

        binding.btnSignup.setOnClickListener(view ->{
            String userName=binding.appUsername.getText().toString();
            String userEmail=binding.appEmail.getText().toString();
            String userPassword=binding.appPassword.getText().toString();

            if(!userName.isEmpty() && !userPassword.isEmpty() && !userEmail.isEmpty()){
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                            Users user = new Users(userName,userEmail,userPassword);
                            String uId= Objects.requireNonNull(task.getResult().getUser()).getUid();
                            database.getReference().child(DATABASE_OBJECT).child(uId).setValue(user);

                            launchActivity(SignUpActivity.this, MainActivity.class);

                        }else {
                            progressDialog.hide();
                            Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            else{
                Toast.makeText(this, "Enter credentials", Toast.LENGTH_SHORT).show();
            }
        });

        binding.appAlreadyHaveAccount.setOnClickListener(view->{
            launchActivity(SignUpActivity.this,SignInActivity.class);
        });

        binding.btnGoogle.setOnClickListener(view ->{
            Toast.makeText(this, "Google Sing Up work in Progress", Toast.LENGTH_SHORT).show();
        });
    }

    // for launching new Activity from current activity
    private void launchActivity(Context currentActivity, Class<?> nextActivity){
        Intent intent = new Intent(currentActivity,nextActivity);
        startActivity(intent);
        this.finish();
    }
}