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

import io.paperdb.Paper;

import static com.example.mainproject.Activities.supplier_login.SUP;

/**
 * A simple {@link Fragment} subclass.
 */
public class Check_sup_individual_items extends Fragment {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<uploadinfo>list;
    LinearLayout linearLayout;

    public Check_sup_individual_items() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_check_sup_individual_items, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("Images");
        recyclerView=view.findViewById(R.id.sup_individual_item_recycler);
        linearLayout=view.findViewById(R.id.linear);
        list=new ArrayList<>();
        final String mail= Paper.book().read(SUP);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    uploadinfo upload=data.getValue(uploadinfo.class);

                    if (upload.getEmail().equals(mail)){
                        list.add(upload);
                    }
                }
                if (list.isEmpty()){
                    linearLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    supplier_adapter_for_all sup=new supplier_adapter_for_all(getContext(),list);
                    recyclerView.setAdapter(sup);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
}
