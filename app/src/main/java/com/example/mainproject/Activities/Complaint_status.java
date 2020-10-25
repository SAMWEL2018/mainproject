package com.example.mainproject.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.MyAdapters.complaint_status_adapter;
import com.example.mainproject.MyModels.FarmerComplaintmodel;
import com.example.mainproject.MyModels.complaint_status_model;
import com.example.mainproject.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
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

import static com.example.mainproject.Commons.Links.COMPLAINT;
import static com.example.mainproject.Commons.Links.COMPLAINT_STATUS;

public class Complaint_status extends AppCompatActivity {
    RecyclerView recyclerView;
    List <FarmerComplaintmodel> list;
    complaint_status_adapter complaint_status_adapter;
    CoordinatorLayout coordinatorLayout;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status);
        coordinatorLayout=findViewById(R.id.coord);
        databaseReference= FirebaseDatabase.getInstance().getReference("Complaints");
        firebaseAuth=FirebaseAuth.getInstance();

        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Posts");

        recyclerView=findViewById(R.id.complaint_status_recycler_view);
        linearLayout=findViewById(R.id.linear_complaints);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        list= new ArrayList<>();

        final ProgressDialog progressDialog= new ProgressDialog(Complaint_status.this);
        progressDialog.setTitle("Loading complaints");
        progressDialog.setMessage("please wait");
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for (DataSnapshot data:dataSnapshot.getChildren()){
                    FarmerComplaintmodel farm=data.getValue(FarmerComplaintmodel.class);
                    list.add(farm);
                }
                if (list.isEmpty()){
                    linearLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else {
                    linearLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    complaint_status_adapter = new complaint_status_adapter(list, Complaint_status.this);
                    recyclerView.setAdapter(complaint_status_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Complaint_status.this, LinearLayoutManager.VERTICAL, false));
                }
                ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        list.remove(viewHolder.getAdapterPosition());
                        complaint_status_adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                        complaint_status_adapter.notifyDataSetChanged();
                        Snackbar snackbar=Snackbar.make(coordinatorLayout,"Deleting post......",Snackbar.LENGTH_LONG)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                    }
                                });
                        snackbar.show();

                    }
                };
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem searchitem=menu.findItem(R.id.searh);
        SearchView searchView= (SearchView) searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                complaint_status_adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}
