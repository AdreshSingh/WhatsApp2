package com.example.whatsapp2.Fragments;

import static com.example.whatsapp2.ui.SignUpActivity.DATABASE_OBJECT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapp2.Adapters.UsersAdapter;
import com.example.whatsapp2.Models.Users;
import com.example.whatsapp2.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ChatsFragment extends Fragment {

    FragmentChatsBinding binding;
    ArrayList<Users> list= new ArrayList<>();
    FirebaseDatabase database;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChatsBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();
        UsersAdapter adapter = new UsersAdapter(list,getContext());
        binding.appChatRecyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.appChatRecyclerView.setLayoutManager(linearLayoutManager);

        final String userUid= FirebaseAuth.getInstance().getUid();
        database.getReference().child(DATABASE_OBJECT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot :
                        snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    Objects.requireNonNull(users).setUserId(dataSnapshot.getKey());
                    if(! users.getUserId().equals(userUid)){
                        list.add(users);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}