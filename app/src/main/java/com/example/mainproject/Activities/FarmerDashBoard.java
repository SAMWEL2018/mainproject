package com.example.mainproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainproject.MyModels.complaint_status_model;
import com.example.mainproject.R;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FarmerDashBoard extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dash_board);
        Paper.init(FarmerDashBoard.this);
        TextView count= findViewById(R.id.count);



    }

    public void rdcomplaint(View view) {
        Intent intent= new Intent(FarmerDashBoard.this, Complaint.class);
        startActivity(intent);
    }


    public void rd_complaint_status(View view) {
//        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(FarmerDashBoard.this,Complaint_status.class);
        startActivity(intent);
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
                Paper.book().destroy();
                startActivity(new Intent(FarmerDashBoard.this,Login.class));
                finish();
                break;
        }
        return true;
    }
}
