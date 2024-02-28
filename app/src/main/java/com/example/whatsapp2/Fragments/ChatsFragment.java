package com.example.whatsapp2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsapp2.Models.Users;
import com.example.whatsapp2.R;
import com.example.whatsapp2.databinding.FragmentChatsBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    FragmentChatsBinding binding;
    ArrayList<Users> list= new ArrayList<>();
    FirebaseDatabase database;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }
}