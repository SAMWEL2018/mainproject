package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainproject.MyAdapters.supplier_item_adapter;
import com.example.mainproject.MyModels.uploadinfo;
import com.example.mainproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Sells_Farmers extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ArrayList<uploadinfo> list;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sells__farmers);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Sales By Sellers");

        recyclerView=findViewById(R.id.sells_farmer_recycler);
        linearLayout=findViewById(R.id.linear_sells);
        databaseReference= FirebaseDatabase.getInstance().getReference("Images");

        recyclerView.setHasFixedSize(true);

        list=new ArrayList<>();
        progressDialog=new ProgressDialog(Sells_Farmers.this);
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

                if (list.isEmpty()){
                    linearLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    progressDialog.dismiss();

                }
                else {
                    linearLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    supplier_item_adapter supplier_item_adapter = new supplier_item_adapter(Sells_Farmers.this, list);
                    recyclerView.setAdapter(supplier_item_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Sells_Farmers.this));
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Sells_Farmers.this, "Error here", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
