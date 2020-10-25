package com.example.mainproject.MyAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Activities.Admin_chats;
import com.example.mainproject.Activities.Chats;
import com.example.mainproject.MyModels.FarmerComplaintmodel;
import com.example.mainproject.MyModels.FarmerConversationmodel;
import com.example.mainproject.R;

import java.util.ArrayList;
import java.util.List;

public class AdminComplaintStatus extends RecyclerView.Adapter<AdminComplaintStatus.item> implements Filterable {

    List<FarmerComplaintmodel>list;
    List<FarmerComplaintmodel> Listfull;
    Context context;


    public AdminComplaintStatus(List<FarmerComplaintmodel> list, Context context) {
        this.list = list;
        Listfull=new ArrayList<>(list);
        this.context = context;
    }

    @NonNull
    @Override
    public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.complaint_status_item,parent,false);

        return new AdminComplaintStatus.item(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final item holder, int position) {
        FarmerComplaintmodel model= list.get(position);

        holder.complaint_status_message.setText(model.getComplaint());
        holder.complaint_status_category.setText(model.getCategory());
        holder.complaint_status_time.setText(model.getTime());
        holder.complaint_status_date.setText(model.getDate());
        holder.Complaint_status_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Admin_chats.class);
                intent.putExtra("Topic",holder.complaint_status_message.getText().toString());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class item extends RecyclerView.ViewHolder{
        TextView complaint_status_date,complaint_status_time,complaint_status_category,complaint_status_message,Complaint_status_comment;

        public item(@NonNull View itemView) {
            super(itemView);
            complaint_status_date=itemView.findViewById(R.id.complaint_status_date);
            complaint_status_time=itemView.findViewById(R.id.complaint_status_time);
            complaint_status_category=itemView.findViewById(R.id.complaint_status_category);
            complaint_status_message=itemView.findViewById(R.id.complaint_status_message);
            Complaint_status_comment=itemView.findViewById(R.id.complaint_status_comment);

        }
    }
    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<FarmerComplaintmodel> filterlist=new ArrayList<>();

           if (constraint==null || constraint.length()==0){
               filterlist.addAll(Listfull);
           }
           else {
               String pattern=constraint.toString().toLowerCase().trim();

               for (FarmerComplaintmodel model : Listfull){

                   if (model.getCategory().toLowerCase().contains(pattern)){
                       filterlist.add(model);
                   }
               }
           }

           FilterResults filterResults=new FilterResults();
           filterResults.values=filterlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

}
