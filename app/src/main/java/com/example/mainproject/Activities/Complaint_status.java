package com.example.mainproject.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.MyAdapters.complaint_status_adapter;
import com.example.mainproject.MyModels.complaint_status_model;
import com.example.mainproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.mainproject.Commons.Links.COMPLAINT;
import static com.example.mainproject.Commons.Links.COMPLAINT_STATUS;

public class Complaint_status extends AppCompatActivity {
    RecyclerView recyclerView;
    List <complaint_status_model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status);
        recyclerView=findViewById(R.id.complaint_status_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        list= new ArrayList<>();
        


        StringRequest stringRequest= new StringRequest(Request.Method.GET, COMPLAINT_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray= new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject= jsonArray.getJSONObject(i);

                        String date= jsonObject.getString("date");//"" values are mapped directly from $Readresults
                        String time= jsonObject.getString("time");
                        String message= jsonObject.getString("complaint");
                        String category= jsonObject.getString("category");

                        complaint_status_model complaint_status_model= new complaint_status_model(date,time,category,message);
                        list.add(complaint_status_model);
                    }

                    complaint_status_adapter complaint_status_adapter= new complaint_status_adapter(list,Complaint_status.this);
                    recyclerView.setAdapter(complaint_status_adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Complaint_status.this));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(Complaint_status.this);
        requestQueue.add(stringRequest);
    }
}
