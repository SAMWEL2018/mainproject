package com.example.mainproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mainproject.MyAdapters.supplier_adapter_for_all;
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

public class Check_sup_item extends Fragment {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<uploadinfo> list;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_check_sup_item, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference("Images");
        recyclerView=view.findViewById(R.id.check_item_recycler);
        linearLayout=view.findViewById(R.id.linear_items);
        list=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    uploadinfo uploadinfo=data.getValue(com.example.mainproject.MyModels.uploadinfo.class);
                    list.add(uploadinfo);
                }
                if (list.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    linearLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    supplier_adapter_for_all supplier_item_adapter = new supplier_adapter_for_all(getActivity(), (ArrayList<uploadinfo>) list);
                    recyclerView.setAdapter(supplier_item_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
}
