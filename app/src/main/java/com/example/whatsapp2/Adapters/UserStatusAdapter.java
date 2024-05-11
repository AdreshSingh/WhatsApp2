package com.example.whatsapp2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp2.Models.Users;
import com.example.whatsapp2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserStatusAdapter extends RecyclerView.Adapter<UserStatusAdapter.ViewHolder> {

    ArrayList<Users> list;
    Context ctx;

    public UserStatusAdapter(ArrayList<Users> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout to be inflated
        View view = LayoutInflater.from(ctx).inflate(R.layout.sample_show_user_status,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user=list.get(position);

        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.avatar3).into(holder.profilePic);

        holder.userName.setText(user.getUserName());
        holder.userStatus.setText(user.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView profilePic;
        TextView userName,userStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.appUsername);
            userStatus = itemView.findViewById(R.id.appUserStatus);
        }
    }
}
