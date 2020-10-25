package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import io.paperdb.Paper;

import static com.example.mainproject.Commons.Links.COMPLAINT_STATUS;

public class FarmerDashBoard extends AppCompatActivity {
    TextView count;
    List <FarmerComplaintmodel> list;
    complaint_status_adapter complaint_status_adapter;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dash_board);
        list= new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("Complaints");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    FarmerComplaintmodel farm=data.getValue(FarmerComplaintmodel.class);
                    list.add(farm);
                }
                count.setText(String.valueOf(list.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        count=findViewById(R.id.count);


        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Dashboard");

        Paper.init(FarmerDashBoard.this);



    }

    public void rd_complaint(View view) {
        Intent intent= new Intent(FarmerDashBoard.this, Complaint.class);
        startActivity(intent);
    }


    public void rd_complaint_status(View view) {
//        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(FarmerDashBoard.this,Complaint_status.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {



        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Exit");
        alert.setMessage("Are you sure you want to exit?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ProgressDialog progressDialog= new ProgressDialog(FarmerDashBoard.this);
                progressDialog.setTitle("Logging out");
                progressDialog.setMessage("please wait");
                progressDialog.show();

                Paper.book().destroy();
                FirebaseAuth.getInstance().signOut();

                Intent intent=new Intent(FarmerDashBoard.this,MainActivity.class);
                startActivity(intent);

                FarmerDashBoard.this.finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();
        Button negative=alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negative.setTextColor(Color.GREEN);
        Button positive=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setTextColor(Color.RED);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_logout:

                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("account");
                builder.setMessage("are you sure you want to exit,you'll be logged out");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Paper.book().destroy();
                        ProgressDialog progressDialog= new ProgressDialog(FarmerDashBoard.this);
                        FirebaseAuth.getInstance().signOut();
                        progressDialog.setTitle("Logging out");
                        progressDialog.setMessage("please wait");
                        progressDialog.show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(FarmerDashBoard.this, MainActivity.class));
                        finish();

                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                Button negative=alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                negative.setTextColor(Color.GREEN);
                Button positive=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positive.setTextColor(Color.RED);
                break;


            case R.id.account:
                startActivity(new Intent(FarmerDashBoard.this,Account_settings.class));
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void rd_documentation(View view) {
        Intent intent=new Intent(FarmerDashBoard.this,FarmerDocumentation.class);
        ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,findViewById(R.id.docy),"docs");
        startActivity(intent,activityOptionsCompat.toBundle());
    }

    public void rd_groupchat(View view) {
        startActivity(new Intent(FarmerDashBoard.this,Groupchat.class));
    }

    public void rd_events(View view) {
        startActivity(new Intent(FarmerDashBoard.this,Farmer_check_events.class));
    }

    public void shop(View view) {
        startActivity(new Intent(FarmerDashBoard.this,Sells_Farmers.class));
    }
}
