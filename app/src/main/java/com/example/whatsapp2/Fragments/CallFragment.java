package com.example.whatsapp2.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapp2.Adapters.UsersTimeStampAdapter;
import com.example.whatsapp2.Models.Users;
import com.example.whatsapp2.databinding.FragmentCallBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CallFragment extends Fragment {

   FragmentCallBinding binding;
   ArrayList<Users> list = new ArrayList<>();
   FirebaseDatabase database;
    public CallFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCallBinding.inflate(inflater,container,false);

        database = FirebaseDatabase.getInstance();

        UsersTimeStampAdapter usersTimeStampAdapter = new UsersTimeStampAdapter(list,getContext());
        binding.appCallRecyclerView.setAdapter(usersTimeStampAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        binding.appCallRecyclerView.setLayoutManager(linearLayoutManager);


        database.getReference().child("User")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Users users = dataSnapshot.getValue(Users.class);
                            list.add(users);
                        }
                        usersTimeStampAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}