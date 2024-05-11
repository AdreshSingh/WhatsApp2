package com.example.whatsapp2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsapp2.R;
import com.example.whatsapp2.databinding.FragmentStatusBinding;

import java.util.zip.Inflater;

public class StatusFragment extends Fragment {

    FragmentStatusBinding binding;

    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatusBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return  binding.getRoot();
    }
}