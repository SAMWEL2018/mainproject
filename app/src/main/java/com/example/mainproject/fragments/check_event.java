package com.example.mainproject.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.MyAdapters.event_adapter;
import com.example.mainproject.MyModels.Admin_event_model;
import com.example.mainproject.MyModels.event_model;
import com.example.mainproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.mainproject.Commons.Links.EVENT_GET;

/**
 * A simple {@link Fragment} subclass.
 */
public class check_event extends Fragment {

    RecyclerView recyclerView;
    List<Admin_event_model> list;
    DatabaseReference databaseReference;

    public check_event() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_check_event, container, false);
        recyclerView=view.findViewById(R.id.event_recyclerview);
        list=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("Events");
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Events");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Admin_event_model model=data.getValue(Admin_event_model.class);
                    list.add(model);
                }
                event_adapter event_adapter=new event_adapter(getContext(),list);
                recyclerView.setAdapter(event_adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), ""+databaseError, Toast.LENGTH_SHORT).show();

            }
        });











        return view;
    }
}
