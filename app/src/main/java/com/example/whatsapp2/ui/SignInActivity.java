package com.example.whatsapp2.ui;

import static com.example.whatsapp2.ui.SignUpActivity.DATABASE_OBJECT;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp2.MainActivity;
import com.example.whatsapp2.Models.Users;
import com.example.whatsapp2.R;
import com.example.whatsapp2.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    GoogleSignInClient mGoogleSignInClient;

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    SignInClient oneTapClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // reason : theme does not include basic app bar so it will be always null.
//        try {
//            Objects.requireNonNull(getSupportActionBar()).hide();
//        }catch (Exception ex){
//            Toast.makeText(this,"error: " +ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }


        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait, Validation is in progress");
        oneTapClient = Identity.getSignInClient(this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_id_token))
                        .requestEmail()
                                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

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

        binding.appBtnGoogle.setOnClickListener(view -> SignIn());

        if (mAuth.getCurrentUser() != null){
            launchActivity(SignInActivity.this, MainActivity.class);
            this.finish();
        }
    }
    // for launching new Activity from current activity
    private void launchActivity(Context currentActivity,Class<?> nextActivity){
        Intent intent = new Intent(currentActivity,nextActivity);
        startActivity(intent);
    }

    public  void SignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("app-signIn", Objects.requireNonNull(account.getId()));
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (Exception ex){
                Log.w("app-signIn", Objects.requireNonNull(ex.getMessage()));
            }
        }

        }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        Log.d("app-signIn", "onComplete: success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        Users users = new Users();
                        users.setUserId(Objects.requireNonNull(user).getUid());
                        users.setUserName(user.getDisplayName());
                        users.setProfilePic(Objects.requireNonNull(user.getPhotoUrl()).toString());
                        firebaseDatabase.getReference()
                                .child(DATABASE_OBJECT)
                                .child(user.getUid())
                                .setValue(users);
//                            updateUI(user);
                        launchActivity(SignInActivity.this, MainActivity.class);
                    }
                    else {
                        Log.w("app-signIn", "onComplete: unsuccessful: "+task.getException());
//                            updateUI(null);
                    }
                });
    }
}