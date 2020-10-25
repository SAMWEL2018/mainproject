package com.example.mainproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.MyModels.uploadinfo;
import com.example.mainproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class supplier_item_adapter extends RecyclerView.Adapter<supplier_item_adapter.item> {

    Context context;
    ArrayList<uploadinfo> list;
    public  boolean likes=false;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    int likescount;

    public supplier_item_adapter(Context context, ArrayList<uploadinfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.sup_item,parent,false);

        return new supplier_item_adapter.item(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final item holder, int position) {

        final uploadinfo uploadinfo=list.get(position);
        databaseReference= FirebaseDatabase.getInstance().getReference("Likes");

        Picasso.get().load(list.get(position).getImageURL()).fit().centerCrop().into(holder.imageView);
        holder.contact.setText(uploadinfo.getSupplier_contact());
        holder.price.setText(uploadinfo.getPrice());
        holder.name.setText(uploadinfo.getImagename());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(uploadinfo.getId()).hasChild(firebaseAuth.getCurrentUser().getUid())){

                    likescount=(int) dataSnapshot.child(uploadinfo.getId()).getChildrenCount();
                    holder.like.setImageResource(R.drawable.ic_like_yes);
                    holder.likesdisplay.setText(likescount +" Likes");
                }
                else {

                    likescount=(int) dataSnapshot.child(uploadinfo.getId()).getChildrenCount();
                    holder.like.setImageResource(R.drawable.ic_like);
                    holder.likesdisplay.setText(likescount +" Likes");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference= FirebaseDatabase.getInstance().getReference("Likes");

                likes=true;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (likes){

                            if (dataSnapshot.child(uploadinfo.getId()).hasChild(firebaseAuth.getCurrentUser().getUid())){

                                databaseReference.child(uploadinfo.getId()).child(firebaseAuth.getCurrentUser().getUid()).removeValue();

                                likes=false;
                            }
                            else {
                                databaseReference.child(uploadinfo.getId()).child(firebaseAuth.getCurrentUser().getUid()).setValue(true);
                                likes=false;
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class item extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,price,contact;
        ImageView like;
        TextView likesdisplay;

        public item(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.sup_item_img);
            name=itemView.findViewById(R.id.sup_item_name);
            price=itemView.findViewById(R.id.sup_item_price);
            contact=itemView.findViewById(R.id.sup_item_contact);
            like=itemView.findViewById(R.id.likee);
            likesdisplay=itemView.findViewById(R.id.likesdisplay);
        }
    }
}
