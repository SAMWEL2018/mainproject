package com.example.mainproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.MyModels.uploadinfo;
import com.example.mainproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class supplier_adapter_for_all extends RecyclerView.Adapter<supplier_adapter_for_all.item> {

    Context context;
    ArrayList<uploadinfo> list;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Likes");

    public supplier_adapter_for_all(Context context, ArrayList<uploadinfo> list) {
        this.context = context;
        this.list = list;
    }
    int empty=0,centre=1;
    int likescount;

    @NonNull
    @Override
    public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            LayoutInflater layoutInflater=LayoutInflater.from(context);
            View view=layoutInflater.inflate(R.layout.sup_item_for_all,parent,false);
            return new supplier_adapter_for_all.item(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final item holder, final int position) {

        final uploadinfo uploadinfo=list.get(position);

//        Picasso.get().load(list.get(position).getImageURL()).fit().centerCrop().into(holder.imageView);
        Picasso.get().load(list.get(position).getImageURL()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {


                    }

                    @Override
                    public void onError(Exception e) {

                        Picasso.get().load(list.get(position).getImageURL()).fit().centerCrop().into(holder.imageView);

                    }
                });

        holder.contact.setText(uploadinfo.getSupplier_contact());
        holder.price.setText(uploadinfo.getPrice());
        holder.name.setText(uploadinfo.getImagename());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                likescount=(int) dataSnapshot.child(uploadinfo.getId()).getChildrenCount();
                holder.likes_count.setText(likescount +" Likes");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }



    class item extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,price,contact,count,likes_count;

        public item(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.sup_item_img);
            name=itemView.findViewById(R.id.sup_item_name);
            price=itemView.findViewById(R.id.sup_item_price);
            contact=itemView.findViewById(R.id.sup_item_contact);
            count=itemView.findViewById(R.id.like_count);
            likes_count=itemView.findViewById(R.id.like_count);

        }
    }
}
