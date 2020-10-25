package com.example.mainproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.MyModels.Chats_model;
import com.example.mainproject.MyModels.FarmerConversationmodel;
import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.paperdb.Paper;

import static com.example.mainproject.Commons.PaperCommons.USERMAIL;

public class Chats_adapter extends RecyclerView.Adapter<Chats_adapter.hold>{
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    public Chats_adapter() {
    }

    int layout_left=0,layout_right=1,layout_centre=2;

    @NonNull
    @Override
    public hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        Paper.init(context);

        if (viewType==layout_left){
            View view=layoutInflater.inflate(R.layout.chat_item_left,parent,false);
            return  new Chats_adapter.hold(view);
        }
        else if (viewType==layout_centre){
            View view=layoutInflater.inflate(R.layout.chat_item_centre,parent,false);
            return  new Chats_adapter.hold(view);

        }
        else {
            View view= layoutInflater.inflate(R.layout.chat_item_right,parent,false);
            return  new Chats_adapter.hold(view);

        }
    }

    @Override
    public int getItemViewType(int position) {
        String mail= firebaseAuth.getCurrentUser().getEmail();
        String admin="ADMIN";
        if (list.get(position).getUsername().equals(admin)){
            return layout_centre;
        }
        else if (list.get(position).getEmail().equals(mail)){
            return layout_left;
        }
        else {
            return layout_right;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull hold holder, int position) {

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

    List<FarmerConversationmodel> list;
    Context context;

    public Chats_adapter(List<FarmerConversationmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    class hold extends RecyclerView.ViewHolder{

        TextView username,reply,date,time;

        public hold(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.user_name);
            reply=itemView.findViewById(R.id.chat_message);
            date= itemView.findViewById(R.id.chat_date);
            time= itemView.findViewById(R.id.chat_time);
        }
    }
}
