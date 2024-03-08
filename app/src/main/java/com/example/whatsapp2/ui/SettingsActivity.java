package com.example.whatsapp2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.whatsapp2.R;
import com.example.whatsapp2.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivitySettingsBinding.inflate(getLayoutInflater());

       setContentView(binding.getRoot());
    }
}