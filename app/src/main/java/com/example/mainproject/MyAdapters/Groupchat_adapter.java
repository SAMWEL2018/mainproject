package com.example.mainproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.MyModels.GroupChat_Conversation_model;
import com.example.mainproject.MyModels.Groupchat_model;
import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.paperdb.Paper;

import static com.example.mainproject.Commons.PaperCommons.USERMAIL;

public class Groupchat_adapter extends RecyclerView.Adapter<Groupchat_adapter.group> {

    int layout_left=0,layout_right=1;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();


    @NonNull
    @Override
    public group onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater= LayoutInflater.from(context);
        Paper.init(context);

        if (viewType==layout_left){
            View view= layoutInflater.inflate(R.layout.chat_item_left,parent,false);
            return new Groupchat_adapter.group(view);
        }
        else{
            View view= layoutInflater.inflate(R.layout.chat_item_right,parent,false);
            return new Groupchat_adapter.group(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final group holder, int position) {

        GroupChat_Conversation_model groupchat_model= list.get(position);

        holder.username.setText(groupchat_model.getUsername());
        holder.message.setText(groupchat_model.getMessage());
        holder.date.setText(groupchat_model.getDate());
        holder.time.setText(groupchat_model.getTime());

        if (position==list.size()-1){
            DatabaseReference seen= FirebaseDatabase.getInstance().getReference("Viewers");
            seen.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int sees= (int) dataSnapshot.getChildrenCount()-1;
                    holder.seen.setText("seen by: "+sees);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                };
            });
        }else {
            holder.seen.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    List<GroupChat_Conversation_model> list;
    Context context;

    public Groupchat_adapter(List<GroupChat_Conversation_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    class group extends RecyclerView.ViewHolder{

        TextView username,message,date,time,seen;

        public group(@NonNull View itemView) {
            super(itemView);

            username= itemView.findViewById(R.id.user_name);
            message=itemView.findViewById(R.id.chat_message);
            date= itemView.findViewById(R.id.chat_date);
            time= itemView.findViewById(R.id.chat_time);
            seen=itemView.findViewById(R.id.see);

        }
    }

    @Override
    public int getItemViewType(int position) {
        String mail= firebaseAuth.getCurrentUser().getEmail();
        if (list.get(position).getEmail().equals(mail)){
            return layout_left;
        }
        else {
            return layout_right;
        }
    }
}
