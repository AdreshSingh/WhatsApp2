package com.example.whatsapp2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp2.Models.Users;
import com.example.whatsapp2.R;
import com.example.whatsapp2.ui.ChatDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{
    ArrayList<Users> list;
    Context ctx;

    public UsersAdapter(ArrayList<Users> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.sample_show_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = list.get(position);
        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.avatar3).into(holder.profilePic);
        holder.userName.setText(user.getUserName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, ChatDetailActivity.class);
            intent.putExtra("userId",user.getUserId());
            intent.putExtra("profilePic",user.getProfilePic());
            intent.putExtra("userName",user.getUserName());
            ctx.startActivity(intent);
        });

        //To get last message
        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid() + user.getUserId())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()){
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                holder.lastMessage.setText(String.valueOf(dataSnapshot.child("message").getValue()));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView userName,lastMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic =itemView.findViewById(R.id.profile_image);
            userName =itemView.findViewById(R.id.appUsername);
            lastMessage = itemView.findViewById(R.id.appLastMessage);
        }
    }
}
