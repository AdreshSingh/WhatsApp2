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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UsersTimeStampAdapter extends  RecyclerView.Adapter<UsersTimeStampAdapter.ViewHolder>{

    ArrayList<Users> list;
    Context ctx;
    public UsersTimeStampAdapter(ArrayList<Users> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.sample_show_user_timestamp,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = list.get(position);
        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.avatar3).into(holder.profilePic);
        holder.userName.setText(user.getUserName());

        Date date = new Date(user.getTimeStamp());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy h:mm a", Locale.getDefault());
        String strDate = simpleDateFormat.format(date);

        holder.userTimeStamp.setText(strDate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView userName,userTimeStamp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.appUsername);
            userTimeStamp = itemView.findViewById(R.id.appUserTimeStamp);
        }
    }
}
