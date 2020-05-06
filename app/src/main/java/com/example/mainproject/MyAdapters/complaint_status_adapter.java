package com.example.mainproject.MyAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Activities.Chats;
import com.example.mainproject.MyModels.complaint_status_model;
import com.example.mainproject.R;

import java.util.List;

public class complaint_status_adapter extends RecyclerView.Adapter<complaint_status_adapter.status_view_holder> {
    List<complaint_status_model> mylist;
    Context context;

    public complaint_status_adapter() {
    }

    public complaint_status_adapter(List<complaint_status_model> mylist, Context context) {
        this.mylist = mylist;
        this.context = context;
    }


    @NonNull
    @Override
    public status_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.complaint_status_item,parent,false);

        return new complaint_status_adapter.status_view_holder(view);
    }
    //matching

    @Override
    public void onBindViewHolder(@NonNull final status_view_holder holder, int position) {

        complaint_status_model model= mylist.get(position);

        holder.complaint_status_message.setText(model.getMessage());
        holder.complaint_status_category.setText(model.getCategory());
        holder.complaint_status_time.setText(model.getTime());
        holder.complaint_status_date.setText(model.getDate());
        holder.Complaint_status_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Chats.class);
                intent.putExtra("Topic",holder.complaint_status_message.getText().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mylist.size();
    }



    //relating to message fragment
    class status_view_holder extends RecyclerView.ViewHolder{
        TextView complaint_status_date,complaint_status_time,complaint_status_category,complaint_status_message,Complaint_status_comment;



        public status_view_holder(@NonNull View itemView) {
            super(itemView);
            complaint_status_date=itemView.findViewById(R.id.complaint_status_date);
            complaint_status_time=itemView.findViewById(R.id.complaint_status_time);
            complaint_status_category=itemView.findViewById(R.id.complaint_status_category);
            complaint_status_message=itemView.findViewById(R.id.complaint_status_message);
            Complaint_status_comment=itemView.findViewById(R.id.complaint_status_comment);
        }
    }
}
