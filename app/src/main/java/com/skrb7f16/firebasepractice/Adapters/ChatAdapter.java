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
import com.skrb7f16.firebasepractice.Models.Messages;
import com.skrb7f16.firebasepractice.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<Messages> messagesModel;
    Context context;

    int SENDER_VIEW_TYPE=1;
    int RECIEVER_VIEW_TYPE=2;


    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView recieverMsg,recieverTime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recieverMsg=itemView.findViewById(R.id.recieverText);
            recieverTime=itemView.findViewById(R.id.recieverTime);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMsg,senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);
        }
    }

    public ChatAdapter(ArrayList<Messages> messagesModel, Context context) {
        this.messagesModel = messagesModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            Log.d("meow","here here");
            View view= LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages message=messagesModel.get(position);

        if(holder.getClass()==SenderViewHolder.class){

            ((SenderViewHolder)holder).senderMsg.setText(message.getMessage());
            ((SenderViewHolder)holder).senderTime.setText(getTimeFromStamp(message.getTimestamp()));
        }
        else if(holder.getClass()==RecieverViewHolder.class){
            Log.d("meow",message.getMessage());
            ((RecieverViewHolder)holder).recieverMsg.setText(message.getMessage());
            ((RecieverViewHolder)holder).recieverTime.setText(getTimeFromStamp(message.getTimestamp()));
        }
    }

    @Override
    public int getItemCount() {
        return messagesModel.size();
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
