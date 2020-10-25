package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mainproject.MyAdapters.supplier_adapter_for_all;
import com.example.mainproject.MyAdapters.supplier_item_adapter;
import com.example.mainproject.MyModels.uploadinfo;
import com.example.mainproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class sells extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ArrayList<uploadinfo> list;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sells);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Sales By Sellers");

        recyclerView=findViewById(R.id.sup_item_recycler);
        databaseReference=FirebaseDatabase.getInstance().getReference("Images");
        databaseReference.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(sells.this));
        recyclerView.setHasFixedSize(true);

        list=new ArrayList<uploadinfo>();
        progressDialog=new ProgressDialog(sells.this);
        progressDialog.setMessage("Loading Items,Please Wait");
        progressDialog.show();



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){

                    uploadinfo sup=data.getValue(uploadinfo.class);
                    list.add(sup);
                    progressDialog.dismiss();

                }
                supplier_adapter_for_all supplier_item_adapter=new supplier_adapter_for_all(sells.this,list);
                recyclerView.setAdapter(supplier_item_adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(sells.this));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(sells.this, "Error here", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
