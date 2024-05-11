package com.example.whatsapp2.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapp2.Adapters.UserStatusAdapter;
import com.example.whatsapp2.Models.Users;
import com.example.whatsapp2.databinding.FragmentStatusBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StatusFragment extends Fragment {

    FragmentStatusBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;

    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatusBinding.inflate(inflater,container,false);
        database=FirebaseDatabase.getInstance();

        UserStatusAdapter statusAdapter = new UserStatusAdapter(list,getContext());
        binding.appStatusRecyclerView.setAdapter(statusAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        binding.appStatusRecyclerView.setLayoutManager(linearLayoutManager);

        database.getReference().child("User")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapShot: snapshot.getChildren()
                             ) {
                            Users users = snapShot.getValue(Users.class);
                            list.add(users);
                        }
                        statusAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // Inflate the layout for this fragment
        return  binding.getRoot();
    }
}