package com.skrb7f16.firebasepractice.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.skrb7f16.firebasepractice.GroupChatActivity;
import com.skrb7f16.firebasepractice.Models.GroupMessageModel;
import com.skrb7f16.firebasepractice.Models.Messages;
import com.skrb7f16.firebasepractice.R;

import java.util.ArrayList;
import java.util.Calendar;

public class GroupAdapter extends RecyclerView.Adapter {


    ArrayList<GroupMessageModel> messagesModel;
    Context context;

    int SENDER_VIEW_TYPE=1;
    int RECIEVER_VIEW_TYPE=2;

    public GroupAdapter(ArrayList<GroupMessageModel> messagesModel, Context context) {
        this.messagesModel = messagesModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender_group,parent,false);
            return new GroupAdapter.SenderViewHolder(view);
        }
        else{
            View view= LayoutInflater.from(context).inflate(R.layout.sample_reciever_group,parent,false);
            return new GroupAdapter.RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        GroupMessageModel message=messagesModel.get(position);

        if(holder.getClass()== GroupAdapter.SenderViewHolder.class){

            ((GroupAdapter.SenderViewHolder)holder).senderMsg.setText(message.getMessage());
            ((GroupAdapter.SenderViewHolder)holder).senderTime.setText(getTimeFromStamp(message.getTimestamp()));

        }
        else if(holder.getClass()== GroupAdapter.RecieverViewHolder.class){
            ((GroupAdapter.RecieverViewHolder)holder).recieverMsg.setText(message.getMessage());
            ((RecieverViewHolder)holder).senderName.setText(message.getSenderName());
            ((GroupAdapter.RecieverViewHolder)holder).recieverTime.setText(getTimeFromStamp(message.getTimestamp()));

        }
    }

    @Override
    public int getItemCount() {
        return messagesModel.size();
    }


    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView recieverMsg,recieverTime,senderName;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recieverMsg=itemView.findViewById(R.id.recieverMessageGroup);
            recieverTime=itemView.findViewById(R.id.recieverTimeGroup);
            senderName=itemView.findViewById(R.id.senderNameGroup);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMsg,senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg=itemView.findViewById(R.id.senderTextGroup);
            senderTime=itemView.findViewById(R.id.senderTimeGroup);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (messagesModel.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECIEVER_VIEW_TYPE;
        }
    }

    public String getTimeFromStamp(long stamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(stamp);
        String date = DateFormat.format("hh:mm aa", calendar).toString();
        return date;
    }
}
