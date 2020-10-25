package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

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

public class Farmer_check_events extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Admin_event_model> list;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_check_events);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Events");

        recyclerView=findViewById(R.id.farmer_event);
        list=new ArrayList<>();
        recyclerView.setNestedScrollingEnabled(true);
        databaseReference= FirebaseDatabase.getInstance().getReference("Events");

        final ProgressDialog progressDialog=new ProgressDialog(Farmer_check_events.this);
        progressDialog.setMessage("Loading events");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                progressDialog.dismiss();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Admin_event_model model=data.getValue(Admin_event_model.class);
                    list.add(model);
                }
                event_adapter event_adapter=new event_adapter(Farmer_check_events.this,list);
                recyclerView.setAdapter(event_adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(Farmer_check_events.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
