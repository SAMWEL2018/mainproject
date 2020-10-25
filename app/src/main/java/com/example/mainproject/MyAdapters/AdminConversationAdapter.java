package com.example.mainproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.MyModels.FarmerConversationmodel;
import com.example.mainproject.R;

import java.util.List;

public class AdminConversationAdapter extends RecyclerView.Adapter<AdminConversationAdapter.item> {

    List<FarmerConversationmodel>list;
    Context context;

    int left=0,right=1;
    public AdminConversationAdapter(List<FarmerConversationmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        if (viewType==left){
            View view=layoutInflater.inflate(R.layout.chat_item_left,parent,false);
            return  new AdminConversationAdapter.item(view);
        }
        else {
            View view= layoutInflater.inflate(R.layout.chat_item_right,parent,false);
            return  new AdminConversationAdapter.item(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull item holder, int position) {

        FarmerConversationmodel chats_model=list.get(position);

        holder.username.setText(chats_model.getUsername());
        holder.reply.setText(chats_model.getReply());
        holder.date.setText(chats_model.getDate());
        holder.time.setText(chats_model.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        String mail="ADMIN";
        if (list.get(position).getUsername().equals(mail)){
            return left;
        }
        else {
            return right;
        }
    }

    class item extends RecyclerView.ViewHolder{
        TextView username,reply,date,time;

        public item(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.user_name);
            reply=itemView.findViewById(R.id.chat_message);
            date= itemView.findViewById(R.id.chat_date);
            time= itemView.findViewById(R.id.chat_time);
        }
    }
}
