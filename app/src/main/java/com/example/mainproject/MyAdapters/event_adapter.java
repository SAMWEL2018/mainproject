package com.example.mainproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.MyModels.Admin_event_model;
import com.example.mainproject.MyModels.event_model;
import com.example.mainproject.R;
import com.example.mainproject.fragments.check_event;

import java.util.List;

public class event_adapter extends RecyclerView.Adapter<event_adapter.event> {
    Context context;
    List<Admin_event_model> list;

    public event_adapter(Context context, List<Admin_event_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public event onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.event_item,parent,false);

        return new event_adapter.event(view);
    }

    @Override
    public void onBindViewHolder(@NonNull event holder, int position) {

        Admin_event_model event_model=list.get(position);

        holder.name.setText(event_model.getName());
        holder.desc.setText(event_model.getDescription());
        holder.location.setText(event_model.getLocation());
        holder.venue.setText(event_model.getVenue());
        holder.contact.setText(event_model.getContact());
        holder.date.setText(event_model.getDate());
        holder.time.setText(event_model.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }





    class event extends RecyclerView.ViewHolder{

        TextView name,desc,location,venue,contact,date,time;

        public event(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.e_name);
            desc=itemView.findViewById(R.id.e_desc);
            location=itemView.findViewById(R.id.e_location);
            venue=itemView.findViewById(R.id.e_venue);
            contact=itemView.findViewById(R.id.e_contact);
            date=itemView.findViewById(R.id.e_date);
            time=itemView.findViewById(R.id.e_time);

        }
    }

}
