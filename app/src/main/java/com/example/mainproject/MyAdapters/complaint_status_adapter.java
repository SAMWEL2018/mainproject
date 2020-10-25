package com.example.mainproject.MyAdapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.Activities.Chats;
import com.example.mainproject.MyModels.FarmerComplaintmodel;
import com.example.mainproject.MyModels.complaint_status_model;
import com.example.mainproject.R;

import java.util.ArrayList;
import java.util.List;

public class complaint_status_adapter extends RecyclerView.Adapter<complaint_status_adapter.status_view_holder> implements Filterable {
    List<FarmerComplaintmodel> mylist;
    List<FarmerComplaintmodel> Listfull;
    Context context;


    public complaint_status_adapter(List<FarmerComplaintmodel> mylist, Context context) {
        this.mylist = mylist;
        Listfull= new ArrayList<>(mylist);
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

        FarmerComplaintmodel model= mylist.get(position);

        holder.complaint_status_message.setText(model.getComplaint());
        holder.complaint_status_category.setText(model.getCategory());
        holder.complaint_status_time.setText(model.getTime());
        holder.complaint_status_date.setText(model.getDate());
        holder.Complaint_status_comment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, Chats.class);
                intent.putExtra("Topic",holder.complaint_status_message.getText().toString());
                ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,holder.complaint_status_message.findViewById(R.id.complaint_status_message),"foward");
                context.startActivity(intent,activityOptionsCompat.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {

        return mylist.size();
    }



    //relating to message fragment
    class status_view_holder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView complaint_status_date,complaint_status_time,complaint_status_category,complaint_status_message,Complaint_status_comment;



        public status_view_holder(@NonNull final View itemView) {
            super(itemView);
            complaint_status_date=itemView.findViewById(R.id.complaint_status_date);
            complaint_status_time=itemView.findViewById(R.id.complaint_status_time);
            complaint_status_category=itemView.findViewById(R.id.complaint_status_category);
            complaint_status_message=itemView.findViewById(R.id.complaint_status_message);
            Complaint_status_comment=itemView.findViewById(R.id.complaint_status_comment);

            itemView.setOnLongClickListener(this);



        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onLongClick(View v) {

            int position=getAdapterPosition();
            Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            PopupMenu popupMenu=new PopupMenu(context,itemView);
            popupMenu.getMenuInflater().inflate(R.menu.popupmenu,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.delete:
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

                    }

                    return true;
                }
            });
            popupMenu.show();


            return true;
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FarmerComplaintmodel> filteredlist= new ArrayList<>();

            if (constraint==null || constraint.length()==0){
                filteredlist.addAll(Listfull);
            }else {
                String filterpattern= constraint.toString().toLowerCase().trim();

                for (FarmerComplaintmodel model: Listfull){
                    if (model.getCategory().toLowerCase().contains(filterpattern)){
                        filteredlist.add(model);
                    }
                }
            }
            FilterResults results= new FilterResults();
            results.values=filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mylist.clear();
            mylist.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };
}
