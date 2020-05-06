package com.example.mainproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.R;
import com.example.mainproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.mainproject.Commons.Links.COMPLAINT;

public class Complaint extends AppCompatActivity {
    TextView date,time;
    Spinner spinner;
    String[] collection={"crop","livestock","Farming tools","Poultry","Fish farming" };
    EditText textarea;
    Button btnpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        spinner= findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(Complaint.this,android.R.layout.simple_dropdown_item_1line,collection);
        spinner.setAdapter(arrayAdapter);

        textarea= findViewById(R.id.complaint_text_area);
        btnpost=findViewById(R.id.btn_post);
        date= findViewById(R.id.date);
        time=findViewById(R.id.time);

//      generate current date and time
        final Date dates= new Date();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        String mydate= simpleDateFormat.format(dates);
        date.setText(mydate);

        SimpleDateFormat timeformat= new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String mytime= timeformat.format(dates);
        time.setText(mytime);

        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinner.getSelectedItem()==null){
                    Toast.makeText(Complaint.this, "Choose category", Toast.LENGTH_SHORT).show();
                }
                else {

                    final String complaint_text = textarea.getText().toString();
                    final String complaint_category = spinner.getSelectedItem().toString();
                    final String complaint_date= date.getText().toString();
                    final String complaint_time= time.getText().toString();

                    StringRequest stringRequest= new StringRequest(Request.Method.POST,COMPLAINT, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response != null) {
                                if (!response.isEmpty()) {
                                    if (response.equals("success")) {
                                        Toast.makeText(Complaint.this, "comment posted", Toast.LENGTH_SHORT).show();
                                        textarea.setText("");

                                    }
                                    else {
                                        Toast.makeText(Complaint.this, "please try again later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>  mymap= new HashMap<>();
                            mymap.put("complaint_text",complaint_text);
                            mymap.put("complaint_category",complaint_category);
                            mymap.put("complaint_date",complaint_date);
                            mymap.put("complaint_time",complaint_time);

                            return mymap;
                        }
                    };

                    RequestQueue requestQueue= Volley.newRequestQueue(Complaint.this);
                    requestQueue.add(stringRequest);
                }

            }
        });

    }
}
