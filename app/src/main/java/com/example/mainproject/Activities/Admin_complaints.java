package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.MyAdapters.AdminComplaintStatus;
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

import static com.example.mainproject.Commons.Links.COMPLAINT_STATUS;

public class Admin_complaints extends AppCompatActivity {

    RecyclerView recyclerView;
    List <FarmerComplaintmodel> list;
    complaint_status_adapter complaint_status_adapter;
    CoordinatorLayout coordinatorLayout;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    AdminComplaintStatus adminComplaintStatus;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaints);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Complaints");

        coordinatorLayout=findViewById(R.id.coord);
        recyclerView= findViewById(R.id.admin_complaints_recycler);
        linearLayout=findViewById(R.id.linear_admin_complaints);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        list= new ArrayList<>();
        progressDialog=new ProgressDialog(Admin_complaints.this);
        progressDialog.setMessage("Loading complaints");
        progressDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference("Complaints");
        firebaseAuth=FirebaseAuth.getInstance();

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
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    adminComplaintStatus = new AdminComplaintStatus(list, Admin_complaints.this);
                    recyclerView.setAdapter(adminComplaintStatus);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Admin_complaints.this));
                }

                ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        list.remove(viewHolder.getAdapterPosition());
                        adminComplaintStatus.notifyItemRemoved(viewHolder.getLayoutPosition());
                        adminComplaintStatus.notifyDataSetChanged();
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

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem search=menu.findItem(R.id.searh);
        SearchView searchView= (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adminComplaintStatus.getFilter().filter(newText);
                return false;
            }
        });




        return true;
    }
}
