package com.example.whatsapp2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp2.Models.MessageModel;
import com.example.whatsapp2.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter {
    ArrayList<MessageModel> messageModels;
    Context ctx;
    String receiverId;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVE_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModel, Context ctx,String receiverId) {
        this.messageModels = messageModel;
        this.ctx = ctx;
        this.receiverId = receiverId;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context ctx) {
        this.messageModels = messageModels;
        this.ctx = ctx;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(ctx).inflate(R.layout.sample_sender,parent,false);
            return  new SenderViewHolder(view);
        }
        View view = LayoutInflater.from(ctx).inflate(R.layout.sample_receiver,parent,false);
        return  new RecieverViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        String userUid = FirebaseAuth.getInstance().getUid();
        if (Objects.requireNonNull(messageModels.get(position)).getUId().equals(userUid)){
            return SENDER_VIEW_TYPE;
        }
        return RECEIVE_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);
        if (holder.getClass() == SenderViewHolder.class){
            assert messageModel != null;
            ((SenderViewHolder)holder).senderMsg.setText(messageModel.getMessage());
        }
       else {
            assert messageModel != null;
            ((RecieverViewHolder)holder).receiverMsg.setText(messageModel.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverMsg,receiverTime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.appReceiverText);
            receiverTime = itemView.findViewById(R.id.appReceiverTime);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg,senderTimeStamp;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.appSenderText);
            senderTimeStamp = itemView.findViewById(R.id.appSenderTime);
        }
    }
}
